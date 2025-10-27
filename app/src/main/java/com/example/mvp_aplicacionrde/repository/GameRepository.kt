package com.example.mvp_aplicacionrde.repository

import com.example.mvp_aplicacionrde.data.local.dao.GameDao
import com.example.mvp_aplicacionrde.data.local.dao.PlayerAnswerDao
import com.example.mvp_aplicacionrde.data.local.dao.ScenarioDao
import com.example.mvp_aplicacionrde.data.local.dao.TriviaQuestionDao
import com.example.mvp_aplicacionrde.data.local.entity.GameEntity
import com.example.mvp_aplicacionrde.data.local.entity.PlayerAnswerEntity
import com.example.mvp_aplicacionrde.data.local.entity.ScenarioEntity
import com.example.mvp_aplicacionrde.data.local.entity.TriviaQuestionEntity
import com.example.mvp_aplicacionrde.network.AiTriviaRequest
import com.example.mvp_aplicacionrde.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class GameRepository(
    private val gameDao: GameDao,
    private val scenarioDao: ScenarioDao,
    private val triviaDao: TriviaQuestionDao,
    private val answerDao: PlayerAnswerDao
) {

    // ======================================================
    // 🧩 ESCENARIOS
    // ======================================================

    suspend fun getScenarioById(id: Int): ScenarioEntity? = withContext(Dispatchers.IO) {
        scenarioDao.getScenarioById(id)
    }

    suspend fun insertScenario(scenario: ScenarioEntity) = withContext(Dispatchers.IO) {
        scenarioDao.insertScenario(scenario)
    }

    // ======================================================
    // 🧠 TRIVIAS
    // ======================================================

    suspend fun getTriviaForScenario(scenarioId: Int): TriviaQuestionEntity? = withContext(Dispatchers.IO) {
        triviaDao.getTriviaForScenario(scenarioId)
    }

    suspend fun insertTriviaQuestion(q: TriviaQuestionEntity) = withContext(Dispatchers.IO) {
        triviaDao.insertTriviaQuestion(q)
    }

    // ======================================================
    // 🧾 RESPUESTAS DEL JUGADOR
    // ======================================================

    suspend fun insertPlayerAnswer(answer: PlayerAnswerEntity) = withContext(Dispatchers.IO) {
        answerDao.insertPlayerAnswer(answer)
    }

    // ======================================================
    // 🕹️ PARTIDAS
    // ======================================================

    suspend fun insertGame(game: GameEntity) = withContext(Dispatchers.IO) {
        gameDao.insertGame(game)
    }

    suspend fun updateGame(game: GameEntity) = withContext(Dispatchers.IO) {
        gameDao.updateGame(game)
    }

    suspend fun getGamesByUser(userId: String): List<GameEntity> = withContext(Dispatchers.IO) {
        gameDao.getGamesByUser(userId)
    }

    // ======================================================
    // 🤖 IA: Solicitar trivia dinámica y guardarla
    // ======================================================

    suspend fun fetchTriviaFromAI(
        scenarioId: Int,
        scenarioDescription: String
    ): TriviaQuestionEntity? = withContext(Dispatchers.IO) {
        try {
            val prompt = """
                Genera una pregunta tipo trivia con 3 opciones de respuesta relacionadas con este escenario:
                "$scenarioDescription".
                Devuelve el texto de la pregunta, las tres opciones y cuál es la correcta.
            """.trimIndent()

            val req = AiTriviaRequest(
                prompt = prompt,
                scenario = scenarioDescription,
                optionsCount = 3
            )

            val resp = RetrofitInstance.api.generateTrivia(req)

            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null && body.options.isNotEmpty()) {
                    val optionsJson = Gson().toJson(body.options)
                    val triviaEntity = TriviaQuestionEntity(
                        id = UUID.randomUUID().toString(),
                        scenarioId = scenarioId,
                        question = body.question,
                        optionsJson = optionsJson,
                        correctAnswer = body.correct_answer
                    )
                    triviaDao.insertTriviaQuestion(triviaEntity)
                    return@withContext triviaDao.getTriviaForScenario(scenarioId)
                }
            }

            // 🚨 Fallback local si falla IA
            val fallbackOptions = listOf("Opción A", "Opción B", "Opción C")
            val fallback = TriviaQuestionEntity(
                id = UUID.randomUUID().toString(),
                scenarioId = scenarioId,
                question = "¿Qué acción es la más segura en esta situación?",
                optionsJson = Gson().toJson(fallbackOptions),
                correctAnswer = "Opción A"
            )
            triviaDao.insertTriviaQuestion(fallback)
            return@withContext triviaDao.getTriviaForScenario(scenarioId)
        } catch (e: Exception) {
            e.printStackTrace()

            // 🚨 Fallback en caso de excepción
            val fallbackOptions = listOf("Opción A", "Opción B", "Opción C")
            val fallback = TriviaQuestionEntity(
                id = UUID.randomUUID().toString(),
                scenarioId = scenarioId,
                question = "¿Qué acción es la más segura en esta situación?",
                optionsJson = Gson().toJson(fallbackOptions),
                correctAnswer = "Opción A"
            )
            triviaDao.insertTriviaQuestion(fallback)
            return@withContext triviaDao.getTriviaForScenario(scenarioId)
        }
    }
}
