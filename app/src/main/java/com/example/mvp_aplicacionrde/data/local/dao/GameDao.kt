package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createGame(game: GameEntity)

    @Update
    suspend fun updateGame(game: GameEntity)

    @Query("SELECT * FROM games WHERE userId = :userId")
    fun getGamesByUser(userId: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE gameId = :gameId LIMIT 1")
    fun getGameById(gameId: String): Flow<GameEntity?>
}