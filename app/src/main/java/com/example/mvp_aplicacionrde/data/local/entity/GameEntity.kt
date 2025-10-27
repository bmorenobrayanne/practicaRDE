package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GameEntity(
    @PrimaryKey val gameId: String,
    val userId: String,
    val createdAt: Long,
    val state: String,
    val currentScenarioId: String?,
    val progressJson: String
)