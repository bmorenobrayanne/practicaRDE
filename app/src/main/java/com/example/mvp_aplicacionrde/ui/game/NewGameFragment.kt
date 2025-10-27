package com.example.mvp_aplicacionrde.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvp_aplicacionrde.R
import com.example.mvp_aplicacionrde.data.local.database.AppDatabase
import com.example.mvp_aplicacionrde.repository.GameRepository
import com.example.mvp_aplicacionrde.repository.UserRepository
import com.example.mvp_aplicacionrde.viewmodel.GameViewModel
import com.example.mvp_aplicacionrde.viewmodel.GameViewModelFactory
import kotlinx.coroutines.launch

class NewGameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels {
        val db = AppDatabase.getDatabase(requireContext())
        val gameRepo = GameRepository(
            db.gameDao(),
            db.scenarioDao(),
            db.triviaDao(),
            db.playerAnswerDao()
        )
        val userRepo = UserRepository(db.userDao())
        GameViewModelFactory(gameRepo, userRepo)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.createNewGame() // ✅ ya sin parámetros
            findNavController().navigate(R.id.action_newGameFragment_to_gameFragment)
        }
    }
}