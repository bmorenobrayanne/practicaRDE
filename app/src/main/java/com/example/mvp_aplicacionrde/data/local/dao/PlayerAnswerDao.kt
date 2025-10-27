package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.PlayerAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: PlayerAnswerEntity)

    @Query("SELECT * FROM player_answers WHERE gameId = :gameId")
    fun getAnswersByGame(gameId: String): Flow<List<PlayerAnswerEntity>>
}