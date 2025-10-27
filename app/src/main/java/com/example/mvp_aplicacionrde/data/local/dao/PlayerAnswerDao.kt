package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvp_aplicacionrde.data.local.entity.PlayerAnswerEntity

@Dao
interface PlayerAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerAnswer(answer: PlayerAnswerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: PlayerAnswerEntity)

    @Query("SELECT * FROM player_answers WHERE gameId = :gameId")
    suspend fun getAnswersForGame(gameId: String): List<PlayerAnswerEntity>

    @Query("DELETE FROM player_answers WHERE gameId = :gameId")
    suspend fun deleteAnswersForGame(gameId: String)
}
