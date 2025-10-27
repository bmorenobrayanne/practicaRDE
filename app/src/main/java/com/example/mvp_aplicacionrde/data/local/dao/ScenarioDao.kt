package com.example.mvp_aplicacionrde.data.local.dao

import androidx.room.*
import com.example.mvp_aplicacionrde.data.local.entity.ScenarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScenario(scenario: ScenarioEntity)

    @Query("SELECT * FROM scenarios WHERE scenarioId = :id LIMIT 1")
    fun getScenarioById(id: String): Flow<ScenarioEntity?>

    @Query("SELECT * FROM scenarios")
    fun getAllScenarios(): Flow<List<ScenarioEntity>>
}