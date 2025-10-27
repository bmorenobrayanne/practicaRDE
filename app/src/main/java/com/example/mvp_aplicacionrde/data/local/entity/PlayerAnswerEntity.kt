package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_answers")
data class PlayerAnswerEntity(
    @PrimaryKey val id: String,
    val gameId: String,
    val scenarioId: Int,
    val answerText: String,
    val answeredAt: Long
)
