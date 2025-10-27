package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_answers")
data class PlayerAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameId: String,
    val questionId: String,
    val selectedIndex: Int,
    val correct: Boolean,
    val answeredAt: Long
)