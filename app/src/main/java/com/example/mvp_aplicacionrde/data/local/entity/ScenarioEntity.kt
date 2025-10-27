package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scenarios")
data class ScenarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                    // 👈 ESTE es el ID real del escenario
    val title: String,
    val description: String,
    val imageUrl: String
)