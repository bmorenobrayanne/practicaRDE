package com.example.mvp_aplicacionrde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

@Entity(tableName = "trivia_questions")
data class TriviaQuestionEntity(
    @PrimaryKey val id: String,          // UUID generado al insertar
    val scenarioId: Int,                 // escenario al que pertenece
    val question: String,                // texto de la pregunta
    val optionsJson: String,             // lista de opciones en formato JSON
    val correctAnswer: String            // respuesta correcta
){
    // 🔹 Función para obtener la lista de opciones desde el JSON almacenado
    fun getOptions(): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(optionsJson, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
