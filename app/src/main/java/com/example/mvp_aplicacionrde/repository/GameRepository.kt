package com.example.mvp_aplicacionrde.repository

import com.example.mvp_aplicacionrde.data.local.dao.GameDao
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class GameRepository(
    private val gameDao: GameDao
) {

    // 🟢 Inserta una nueva partida localmente (Room)
    suspend fun insertGame(game: GameEntity) = withContext(Dispatchers.IO) {
        gameDao.insertGame(game)
    }

    // 🟣 Obtiene todas las partidas de un usuario
    suspend fun getGamesByUser(userId: String): List<GameEntity> = withContext(Dispatchers.IO) {
        gameDao.getGamesByUser(userId)
    }

    // 🟡 Obtiene una partida específica
    suspend fun getGameById(gameId: String): GameEntity? = withContext(Dispatchers.IO) {
        gameDao.getGameById(gameId)
    }

    // 🔵 Actualiza estado o progreso
    suspend fun updateGame(game: GameEntity) = withContext(Dispatchers.IO) {
        gameDao.updateGame(game)
    }

    // 🚀 (Opcional) Sincronizar con Firebase (por implementar más adelante)
    suspend fun syncGameWithFirebase(game: GameEntity) {
        // Aquí luego integrarás Firestore para sincronización remota
    }
}