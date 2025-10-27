package com.example.mvp_aplicacionrde.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.mvp_aplicacionrde.R
import com.example.mvp_aplicacionrde.viewmodel.UserViewModel

class MainMenuFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)

        val txtPlayerName = view.findViewById<TextView>(R.id.txtPlayerName)
        val imgAvatar = view.findViewById<ImageView>(R.id.imgAvatar)
        val btnNewGame = view.findViewById<Button>(R.id.btnNewGame)
        val btnContinueGame = view.findViewById<Button>(R.id.btnContinueGame)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Observa el usuario actual
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                txtPlayerName.text = user.uid.take(8) // nombre temporal si es anónimo
                imgAvatar.load(R.drawable.ic_launcher_foreground) // imagen temporal
            } else {
                txtPlayerName.text = "Sin usuario"
            }
        }

        // Nueva partida → va al GameFragment
        btnNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }

        // Continuar partida → por ahora placeholder
        btnContinueGame.setOnClickListener {
            // TODO: Implementar lógica de carga desde Room/Firebase
        }

        // Cerrar sesión → FirebaseAuth.signOut() y volver al login
        btnLogout.setOnClickListener {
            userViewModel.signOut()
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }

        // Nueva partida → va al NewGameFragment
        btnNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_newGameFragment)
        }

        return view
    }
}