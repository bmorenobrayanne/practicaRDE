package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> get() = _currentUser

    init {
        // Carga inicial
        _currentUser.value = auth.currentUser
    }

    fun refreshUser() {
        _currentUser.value = auth.currentUser
    }

    fun signOut() {
        viewModelScope.launch {
            auth.signOut()
            _currentUser.postValue(null)
        }
    }
}