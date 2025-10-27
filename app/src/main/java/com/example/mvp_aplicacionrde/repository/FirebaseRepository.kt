package com.example.mvp_aplicacionrde.repository

import com.example.mvp_aplicacionrde.network.AuthService
import com.example.mvp_aplicacionrde.network.FirebaseService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    suspend fun signInAnonymously(): FirebaseUser? {
        return AuthService.signInAnonymously()
    }

    suspend fun testFirestoreConnection(uid: String?) {
        val data = hashMapOf(
            "mensaje" to "Conexión Firestore exitosa ✅",
            "uid" to uid
        )
        FirebaseService.getFirestore().collection("test").add(data).await()
    }
}