package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvp_aplicacionrde.data.local.entity.*
import com.example.mvp_aplicacionrde.repository.GameRepository
import com.example.mvp_aplicacionrde.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GameViewModel(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _scenarioDescription = MutableLiveData<String>()
    val scenarioDescription: LiveData<String> get() = _scenarioDescription

    private val _scenarioImageUrl = MutableLiveData<String>()
    val scenarioImageUrl: LiveData<String> get() = _scenarioImageUrl

    private val _question = MutableLiveData<String>()
    val question: LiveData<String> get() = _question

    private val _options = MutableLiveData<List<String>>()
    val options: LiveData<List<String>> get() = _options

    private val _feedback = MutableLiveData<String>()
    val feedback: LiveData<String> get() = _feedback

    private var currentGame: GameEntity? = null
    private var correctAnswer: String? = null

    /**
     * Crea una nueva partida si no existe.
     */
    fun createNewGame() {
        viewModelScope.launch {
            val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return@launch
            val userId = firebaseUser.uid

            // 🟢 Verificar usuario
            var user = userRepository.getUserById(userId)
            if (user == null) {
                user = UserEntity(
                    userId = userId,
                    email = firebaseUser.email ?: "anonimo@rde.com",
                    displayName = firebaseUser.displayName ?: "Jugador Anónimo",
                    createdAt = System.currentTimeMillis(),
                    avatarUrl = firebaseUser.photoUrl?.toString()
                )
                userRepository.insertUser(user)
            }

            // 🟣 Crear partida nueva
            val newGame = GameEntity(
                gameId = UUID.randomUUID().toString(),
                userId = userId,
                createdAt = System.currentTimeMillis(),
                state = "in_progress",
                currentScenarioId = 1,
                progressJson = "{}"
            )

            gameRepository.insertGame(newGame)
            currentGame = newGame
        }
    }

    /**
     * Carga el escenario actual del jugador.
     */
    suspend fun loadCurrentScenario(): ScenarioEntity? {
        return withContext(Dispatchers.IO) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return@withContext null
            val userId = firebaseUser.uid

            val games = gameRepository.getGamesByUser(userId)
            val currentGame = games.lastOrNull() ?: return@withContext null
            this@GameViewModel.currentGame = currentGame

            val scenarioId = currentGame.currentScenarioId ?: 1
            var scenario = gameRepository.getScenarioById(scenarioId)

            // Si no existe, crear uno base
            if (scenario == null) {
                scenario = ScenarioEntity(
                    id = scenarioId,
                    title = "Escenario $scenarioId",
                    description = "Te encuentras en una nueva situación desafiante.",
                    imageUrl = "https://picsum.photos/600/400?random=$scenarioId"
                )
                gameRepository.insertScenario(scenario)
            }

            scenario
        }
    }

    /**
     * Carga la trivia del escenario actual (desde Room o IA).
     */
    suspend fun loadTriviaForScenario(scenarioId: Int): TriviaQuestionEntity {
        return withContext(Dispatchers.IO) {
            var trivia = gameRepository.getTriviaForScenario(scenarioId)

            // Si no hay trivia local, la genera desde la IA
            if (trivia == null) {
                val scenario = gameRepository.getScenarioById(scenarioId)
                trivia = gameRepository.fetchTriviaFromAI(
                    scenarioId,
                    scenario?.description ?: "Escenario desconocido"
                ) ?: TriviaQuestionEntity(
                    id = UUID.randomUUID().toString(),
                    scenarioId = scenarioId,
                    question = "¿Qué deberías hacer primero?",
                    optionsJson = """["Esperar", "Avanzar", "Regresar"]""",
                    correctAnswer = "Esperar"
                )
                gameRepository.insertTriviaQuestion(trivia)
            }

            // Actualizar LiveData para la UI
            withContext(Dispatchers.Main) {
                _question.value = trivia.question
                _options.value = trivia.getOptions()
                correctAnswer = trivia.correctAnswer
            }

            trivia
        }
    }

    /**
     * Registra la respuesta del jugador y avanza.
     */
    fun registerAnswer(selectedOption: String) {
        viewModelScope.launch {
            val game = currentGame ?: return@launch
            val scenarioId = game.currentScenarioId ?: return@launch

            val answerEntity = PlayerAnswerEntity(
                id = UUID.randomUUID().toString(),
                gameId = game.gameId,
                scenarioId = scenarioId,
                answerText = selectedOption,
                answeredAt = System.currentTimeMillis()
            )
            gameRepository.insertPlayerAnswer(answerEntity)

            // Actualiza progreso JSON
            val updatedProgress = """
                {
                    "scenario_$scenarioId": "$selectedOption"
                }
            """.trimIndent()

            val newProgressJson = if (game.progressJson == "{}") {
                updatedProgress
            } else {
                game.progressJson.dropLast(1) + ", $updatedProgress".drop(1)
            }

            game.progressJson = newProgressJson
            gameRepository.updateGame(game)

            // Avanza de escenario
            advanceScenario()
        }
    }

    /**
     * Avanza al siguiente escenario.
     */
    private suspend fun advanceScenario() = withContext(Dispatchers.IO) {
        currentGame?.let {
            val nextId = (it.currentScenarioId ?: 1) + 1
            it.currentScenarioId = nextId
            gameRepository.updateGame(it)

            withContext(Dispatchers.Main) {
                viewModelScope.launch { loadTriviaForScenario(nextId) }
            }
        }
    }
}
