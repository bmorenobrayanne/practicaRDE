package com.example.mvp_aplicacionrde.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvp_aplicacionrde.data.local.dao.*
import com.example.mvp_aplicacionrde.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        ScenarioEntity::class,
        TriviaQuestionEntity::class,
        PlayerAnswerEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAOs disponibles para toda la app
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun scenarioDao(): ScenarioDao
    abstract fun triviaDao(): TriviaDao
    abstract fun playerAnswerDao(): PlayerAnswerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rde_app_database"
                )
                    // ⚠️ Durante desarrollo: destruye DB si hay cambios de versión
                    .fallbackToDestructiveMigration(false)
                    // ⚙️ (opcional) habilitar logs de consultas SQL
                    // .setQueryCallback({ sqlQuery, bindArgs ->
                    //     Log.d("RoomQuery", "SQL: $sqlQuery Args: $bindArgs")
                    // }, Executors.newSingleThreadExecutor())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}