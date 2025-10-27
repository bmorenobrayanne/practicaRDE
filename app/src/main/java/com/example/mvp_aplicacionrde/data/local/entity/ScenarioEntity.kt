package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scenarios")
data class ScenarioEntity(
    @PrimaryKey val scenarioId: String,
    val title: String,
    val description: String,
    val imageLocalPath: String?,
    val imageRemoteUrl: String?
)