package com.example.mvp_aplicacionrde.network

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

object AuthService {

    suspend fun signInAnonymously(): FirebaseUser? {
        val result = FirebaseService.getAuth().signInAnonymously().await()
        return result.user
    }
}