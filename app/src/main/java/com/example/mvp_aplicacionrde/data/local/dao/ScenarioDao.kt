package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.ScenarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScenario(scenario: ScenarioEntity)

    @Query("SELECT * FROM scenarios WHERE id = :id LIMIT 1")
    suspend fun getScenarioById(id: Int): ScenarioEntity?

    @Query("SELECT * FROM scenarios")
    suspend fun getAllScenarios(): List<ScenarioEntity>

    @Delete
    suspend fun deleteScenario(scenario: ScenarioEntity)
}