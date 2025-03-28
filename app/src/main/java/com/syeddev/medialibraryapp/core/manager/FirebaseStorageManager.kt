package com.syeddev.medialibraryapp.core.manager

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.syeddev.medialibraryapp.core.apiutils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseStorageManager{
    suspend fun uploadMedia(fileName: String,mediaUri: Uri,mediaType: String,metadata: Map<String, Any>): Flow<Resource<String>>

    suspend fun deleteMedia(nodeId: String): Resource<Unit>
}

class FirebaseStorageManagerImpl @Inject constructor(
    val firebaseStorage: FirebaseStorage,
    val firestore: FirebaseFirestore
): FirebaseStorageManager {

    override suspend fun uploadMedia(
        fileName: String,
        mediaUri: Uri,
        mediaType: String,
        metadata: Map<String, Any>
    ) : Flow<Resource<String>> = callbackFlow {
        val storageRefName = firebaseStorage.reference.child("${mediaType}/${fileName}")
        val fileUploadTask = storageRefName.putFile(mediaUri).await()
        val downloadUrl = storageRefName.downloadUrl.await().toString()

        val mediaData = metadata.toMutableMap().apply {
            put("downloadUrl", downloadUrl)
            put("fileType", mediaType)
            put("createdAt", System.currentTimeMillis())
        }

        firestore.collection("media").add(mediaData).addOnSuccessListener { data->
            trySend(Resource.Success(data = data.id))
            close()
        }
        awaitClose()
    }

    override suspend fun deleteMedia(nodeId: String): Resource<Unit> {
        //val nodeReference = firestore.collection(nodeId)
        return Resource.Success()
    }
}