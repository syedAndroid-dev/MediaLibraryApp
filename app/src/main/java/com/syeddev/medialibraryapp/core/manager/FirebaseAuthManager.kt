package com.syeddev.medialibraryapp.core.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.syeddev.medialibraryapp.core.apiutils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseAuthManager {
    suspend fun signUp(name: String, email: String, password: String): Flow<Resource<Unit>>

    suspend fun signIn(email: String, password: String): Flow<Resource<Unit>>

    fun signOut()

    fun getCurrentUser(): FirebaseUser?
}


class FirebaseAuthManagerImpl @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseAuthManager {

    override suspend fun signUp(
        name : String,
        email: String,
        password: String
    ): Flow<Resource<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                trySend(Resource.Success())
            } else {
                trySend(Resource.Error(message = task.exception?.message))
            }
            close()
        }
        awaitClose()
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Unit>> = callbackFlow {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                trySend(Resource.Success())
            } else {
                trySend(Resource.Error(message = task.exception?.message))
            }
            close()
        }
        awaitClose()
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser() = auth.currentUser

}