package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvp_aplicacionrde.repository.FirebaseRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class FirebaseViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    fun startAnonymousAuth() {

        viewModelScope.launch {
            try {
                _status.postValue("Iniciando autenticación anónima...")
                val user = repository.signInAnonymously()
                _status.postValue("Autenticación exitosa ✅ UID: ${user?.uid}")
                _status.postValue("Probando conexión con Firestore...")
                repository.testFirestoreConnection(user?.uid)
                _status.postValue("Conexión a Firestore exitosa ✅")

            } catch (e: Exception) {
                _status.postValue("Error: ${e.message}")
            }
        }

    }

}