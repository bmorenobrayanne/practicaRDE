package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity

@Dao
interface GameDao {

    // 🔹 Inserta una nueva partida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    // 🔹 Actualiza el progreso o estado de una partida
    @Update
    suspend fun updateGame(game: GameEntity)

    // 🔹 Obtiene todas las partidas asociadas a un usuario
    @Query("SELECT * FROM games WHERE userId = :userId")
    suspend fun getGamesByUser(userId: String): List<GameEntity>

    // 🔹 Obtiene una partida específica por su ID
    @Query("SELECT * FROM games WHERE gameId = :gameId LIMIT 1")
    suspend fun getGameById(gameId: String): GameEntity?

    // 🔹 Elimina todas las partidas de un usuario (opcional, por si se reinicia)
    @Query("DELETE FROM games WHERE userId = :userId")
    suspend fun deleteGamesByUser(userId: String)

    // 🔹 Elimina una partida específica (opcional)
    @Query("DELETE FROM games WHERE gameId = :gameId")
    suspend fun deleteGameById(gameId: String)
}
