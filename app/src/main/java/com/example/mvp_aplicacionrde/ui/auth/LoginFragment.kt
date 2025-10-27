package com.example.mvp_aplicacionrde.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvp_aplicacionrde.R
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()
        val btnLoginAnon = view.findViewById<Button>(R.id.btnLoginAnon)

        btnLoginAnon.setOnClickListener {
            signInAnonymously()
        }

        return view
    }

    private fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
                } else {
                    Toast.makeText(requireContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }
}