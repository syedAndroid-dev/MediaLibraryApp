package com.syeddev.medialibraryapp.core.manager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseAuthManager {
    suspend fun signUp(email: String, password: String): Result<Boolean>

    suspend fun signIn(email: String, password: String): Result<Boolean>

    fun signOut()

    fun getCurrentUser(): FirebaseUser?
}


class FirebaseAuthManagerImpl @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseAuthManager {

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser() = auth.currentUser


}