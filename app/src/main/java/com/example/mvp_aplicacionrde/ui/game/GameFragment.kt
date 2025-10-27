package com.example.mvp_aplicacionrde.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.mvp_aplicacionrde.R
import com.example.mvp_aplicacionrde.data.local.database.AppDatabase
import com.example.mvp_aplicacionrde.repository.GameRepository
import com.example.mvp_aplicacionrde.repository.UserRepository
import com.example.mvp_aplicacionrde.viewmodel.GameViewModel
import com.example.mvp_aplicacionrde.viewmodel.GameViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

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

    private lateinit var imgScenario: ImageView
    private lateinit var txtDescription: TextView
    private lateinit var txtQuestion: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnAnswer: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgScenario = view.findViewById(R.id.imgScenario)
        txtDescription = view.findViewById(R.id.txtScenarioDescription)
        txtQuestion = view.findViewById(R.id.txtQuestion)
        radioGroup = view.findViewById(R.id.radioGroupOptions)
        btnAnswer = view.findViewById(R.id.btnAnswer)

        lifecycleScope.launch {
            // 🔹 Cargar escenario inicial
            val scenario = viewModel.loadCurrentScenario()
            scenario?.let { scn ->
                txtDescription.text = scn.description
                imgScenario.load(scn.imageUrl)

                // 🔸 Cargar la trivia asociada
                val trivia = viewModel.loadTriviaForScenario(scn.id)

                txtQuestion.text = trivia.question

                // Convertir JSON a lista
                val options: List<String> = try {
                    Gson().fromJson(trivia.optionsJson, Array<String>::class.java).toList()
                } catch (e: Exception) {
                    listOf("Opción A", "Opción B", "Opción C")
                }

                radioGroup.removeAllViews()
                options.forEach { option ->
                    val rb = RadioButton(requireContext())
                    rb.text = option
                    rb.textSize = 16f
                    radioGroup.addView(rb)
                }
            } ?: run {
                Toast.makeText(requireContext(), "No se encontró escenario", Toast.LENGTH_SHORT).show()
            }
        }

        btnAnswer.setOnClickListener {
            val checked = radioGroup.checkedRadioButtonId
            if (checked == -1) {
                Toast.makeText(requireContext(), "Selecciona una respuesta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedOption = view.findViewById<RadioButton>(checked).text.toString()

            lifecycleScope.launch {
                viewModel.registerAnswer(selectedOption)
                Toast.makeText(requireContext(), "Respuesta guardada ✅", Toast.LENGTH_SHORT).show()
                // 🚀 Aquí podrías avanzar automáticamente al siguiente escenario
            }
        }
    }
}
