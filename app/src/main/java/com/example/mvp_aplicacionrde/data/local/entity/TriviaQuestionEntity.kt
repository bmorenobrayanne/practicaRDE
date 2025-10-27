package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trivia_questions",
    indices = [Index(value = ["scenarioId"])]
)
data class TriviaQuestionEntity(
    @PrimaryKey val questionId: String,
    val scenarioId: String,
    val question: String,
    val optionsJson: String,
    val correctOptionIndex: Int
)