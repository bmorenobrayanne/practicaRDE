package com.example.mvp_aplicacionrde.network

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

object FirebaseService {

    private var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            FirebaseApp.initializeApp(context)
            isInitialized = true
        }
    }

    // 🔹 Retornar las instancias bajo demanda (sin mantenerlas en memoria)
    fun getAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    fun getFirestore(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder().build()
        firestore.firestoreSettings = settings
        return firestore
    }

    fun getStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}
