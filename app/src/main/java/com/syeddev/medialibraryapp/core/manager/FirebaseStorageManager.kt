package com.syeddev.medialibraryapp.core.manager

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.utils.Constants
import com.syeddev.medialibraryapp.core.utils.formatFileSize
import com.syeddev.medialibraryapp.core.utils.valueOrDefault
import com.syeddev.medialibraryapp.features.mediagallery.data.local.MediaItemModel
import com.syeddev.medialibraryapp.features.mediagallery.data.model.MediaItemFireStoreModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseStorageManager {
    var lastVisibleDocument: DocumentSnapshot?

    suspend fun uploadMedia(
        fileName: String,
        mediaUri: Uri,
        mediaType: String,
        metadata: Map<String, Any>
    ): Flow<Resource<MediaItemModel>>

    suspend fun deleteMedia(nodeId: String): Resource<Unit>

    suspend fun getMediaDetail(documentId: String): Flow<Resource<MediaItemFireStoreModel>>

    suspend fun getAllMediaDetails(): Flow<Resource<List<MediaItemFireStoreModel>>>
}

class FirebaseStorageManagerImpl @Inject constructor(
    val firebaseStorage: FirebaseStorage,
    val firestore: FirebaseFirestore
) : FirebaseStorageManager {

    override var lastVisibleDocument: DocumentSnapshot? = null

    override suspend fun uploadMedia(
        fileName: String,
        mediaUri: Uri,
        mediaType: String,
        metadata: Map<String, Any>
    ): Flow<Resource<MediaItemModel>> = callbackFlow {
        val storageRefName = firebaseStorage.reference.child("${mediaType}/${fileName}")
        val fileUploadTask = storageRefName.putFile(mediaUri).await()
        val downloadUrl = storageRefName.downloadUrl.await().toString()

        val mediaData = metadata.toMutableMap().apply {
            put("downloadUrl", downloadUrl)
        }
        firestore.collection("MediaGallery").add(mediaData).addOnSuccessListener { data ->
            trySend(
                Resource.Success(
                    data = MediaItemModel(
                        fireStoreId = data.id,
                        title = fileName,
                        mediaType = mediaType,
                        size = metadata.get("size") as String,
                        uploadedTime = metadata.get("uploadedTime") as Long,
                        isMusic = metadata["isMusic"] as Boolean,
                        musicDetails = metadata["musicDetails"] as String,
                        downloadUrl = downloadUrl
                    )
                )
            )
            close()
        }.addOnFailureListener {
            Log.e("FileUploadError", "Unable to upload image : ${it.message}")
        }
        awaitClose()
    }

    override suspend fun deleteMedia(nodeId: String): Resource<Unit> {
        //val nodeReference = firestore.collection(nodeId)
        return Resource.Success()
    }

    override suspend fun getMediaDetail(documentId: String): Flow<Resource<MediaItemFireStoreModel>> =
        callbackFlow {
                firestore.collection(Constants.FIRE_STORE_COLLECTION_NAME).document(documentId)
                    .get()
                    .addOnSuccessListener { data ->
                        trySend(Resource.Success(data = data.toObject(MediaItemFireStoreModel::class.java)))
                        close()
                    }.addOnFailureListener { exception ->
                        trySend(Resource.Error(message = exception.message.valueOrDefault()))
                        close()
                    }
            awaitClose()
        }

    override suspend fun getAllMediaDetails(): Flow<Resource<List<MediaItemFireStoreModel>>> =
        callbackFlow {
            val query = firestore.collection(Constants.FIRE_STORE_COLLECTION_NAME)
                .orderBy("uploadedTime", Query.Direction.DESCENDING)
                .limit(10)

            val paginatedQuery = lastVisibleDocument?.let { query.startAfter(it) } ?: query

            paginatedQuery.get()
                .addOnSuccessListener { result ->
                    val mediaList = result.map { document ->
                        MediaItemFireStoreModel(
                             title = document.data["title"].toString(),
                             mediaType = document.data["mediaType"].toString(),
                             size =document.data["size"].toString(),
                             uploadedTime = document.data["uploadedTime"] as Long,
                             isMusic = document.data["isMusic"] as Boolean,
                             musicDetails= document.data["musicDetails"].toString(),
                             downloadUrl = document.data["downloadUrl"].toString(),
                             fireStoreId =  document.id
                        )
                    }
                    if (result.documents.isNotEmpty()) {
                        lastVisibleDocument = result.documents.last()
                    }
                    trySend(Resource.Success(mediaList))
                    close()
                }
                .addOnFailureListener { exception ->
                    trySend(Resource.Error(message = exception.message))
                    close()
                }

            awaitClose()
        }
}