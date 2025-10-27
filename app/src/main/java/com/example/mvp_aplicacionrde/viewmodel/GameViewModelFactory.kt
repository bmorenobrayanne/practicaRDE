package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvp_aplicacionrde.repository.GameRepository
import com.example.mvp_aplicacionrde.repository.UserRepository

class GameViewModelFactory(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(gameRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}