package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.TriviaQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TriviaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<TriviaQuestionEntity>)

    @Query("SELECT * FROM trivia_questions WHERE scenarioId = :scenarioId")
    fun getQuestionsByScenario(scenarioId: String): Flow<List<TriviaQuestionEntity>>
}