package com.example.mvp_aplicacionrde.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Update
    suspend fun updateGame(game: GameEntity)

    @Query("SELECT * FROM games WHERE userId = :userId")
    suspend fun getGamesByUser(userId: String): List<GameEntity>

    @Query("SELECT * FROM games WHERE gameId = :gameId LIMIT 1")
    suspend fun getGameById(gameId: String): GameEntity?
}