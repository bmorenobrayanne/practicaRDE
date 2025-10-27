package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity
import com.example.mvp_aplicacionrde.data.local.entity.UserEntity
import com.example.mvp_aplicacionrde.repository.GameRepository
import com.example.mvp_aplicacionrde.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.*

class GameViewModel(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun createNewGame() {
        viewModelScope.launch {
            val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return@launch
            val userId = firebaseUser.uid

            // 🟢 Verificar si el usuario ya existe en Room
            var user = userRepository.getUserById(userId)
            if (user == null) {
                // 🔵 Crear nuevo UserEntity con tus campos actuales
                user = UserEntity(
                    userId = userId,
                    email = firebaseUser.email ?: "anonimo@rde.com",
                    displayName = firebaseUser.displayName ?: "Jugador Anónimo",
                    createdAt = System.currentTimeMillis(),
                    avatarUrl = firebaseUser.photoUrl?.toString()
                )
                userRepository.insertUser(user)
            }

            // 🟣 Crear y guardar la nueva partida
            val newGame = GameEntity(
                gameId = UUID.randomUUID().toString(),
                userId = userId,
                createdAt = System.currentTimeMillis(),
                state = "in_progress",
                currentScenarioId = null,
                progressJson = "{}"
            )
            gameRepository.insertGame(newGame)
        }
    }
}