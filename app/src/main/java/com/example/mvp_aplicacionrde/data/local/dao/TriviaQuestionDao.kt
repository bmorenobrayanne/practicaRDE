package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.TriviaQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaQuestionDao {

    @Query("SELECT * FROM trivia_questions WHERE scenarioId = :scenarioId LIMIT 1")
    suspend fun getTriviaForScenario(scenarioId: Int): TriviaQuestionEntity?

    // 🟢 Insertar o actualizar una trivia
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTriviaQuestion(q: TriviaQuestionEntity)
}