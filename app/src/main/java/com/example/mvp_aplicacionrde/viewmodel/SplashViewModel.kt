package com.example.mvp_aplicacionrde.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvp_aplicacionrde.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    // StateFlow que contiene la URL de la imagen a mostrar
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    // Lista de desastres naturales para la selección aleatoria
    private val disasterTopics = listOf(
        "earthquake",
        "flood disaster",
        "wildfire",
        "hurricane",
        "tsunami",
        "volcano eruption",
        "storm"
    )

    // Imagen de respaldo en caso de fallo
    private val fallbackImage = "https://images.unsplash.com/photo-1506744038136-46273834b3fb"

    init {
        fetchRandomDisasterImage()
    }

    private fun fetchRandomDisasterImage() {
        viewModelScope.launch {
            try {
                // Escoge un desastre aleatorio
                val randomTopic = disasterTopics.random()

                // Llama a la API de Unsplash con el query
                val response = RetrofitClient.unsplashApi.getRandomImage(query = randomTopic)

                // Actualiza el StateFlow con la URL de la imagen recibida
                _imageUrl.value = response.urls.regular
                println("✅ Imagen Unsplash cargada: ${response.urls.regular}")

            } catch (e: Exception) {
                // Si algo falla, imprime el error y usa la imagen de respaldo
                e.printStackTrace()
                _imageUrl.value = fallbackImage
                println("⚠️ Error cargando imagen, usando fallback: $fallbackImage")
            }
        }
    }
}