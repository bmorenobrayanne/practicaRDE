package com.example.mvp_aplicacionrde.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mvp_aplicacionrde.R
import com.example.mvp_aplicacionrde.network.FirebaseService
import com.example.mvp_aplicacionrde.viewmodel.FirebaseViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Inicializar Firebase una sola vez
        FirebaseService.initialize(this)

        // ✅ Observar estado desde el ViewModel
        viewModel.status.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        // ✅ Iniciar sesión anónima
        viewModel.startAnonymousAuth()
    }
}