package com.syeddev.medialibraryapp.features.auth.data.repository

import com.google.firebase.auth.FirebaseUser
import com.syeddev.medialibraryapp.core.apiutils.Resource
import com.syeddev.medialibraryapp.core.manager.FirebaseAuthManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(val firebaseAuthManager: FirebaseAuthManager){

    suspend fun signUp(name: String, email: String, password: String): Flow<Resource<Unit>>{
        return firebaseAuthManager.signUp(name,email,password)
    }


    suspend fun signIn(email: String, password: String): Flow<Resource<Unit>>{
        return firebaseAuthManager.signIn(email,password)
    }

    fun signOut(){
        firebaseAuthManager.signOut()
    }

    fun getCurrentUser(): FirebaseUser?{
        return firebaseAuthManager.getCurrentUser()
    }
}