package com.example.mvp_aplicacionrde.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class AiTriviaRequest(
    val prompt: String,
    val scenario: String,
    val optionsCount: Int = 3
)

data class AiTriviaResponse(
    val question: String,
    val options: List<String>,
    val correct_answer: String
)

interface AiApiService {
    @Headers("Content-Type: application/json")
    @POST("generate-trivia")
    suspend fun generateTrivia(@Body request: AiTriviaRequest): Response<AiTriviaResponse>
}