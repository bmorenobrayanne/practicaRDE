package com.example.mvp_aplicacionrde.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.mvp_aplicacionrde.R
import com.example.mvp_aplicacionrde.viewmodel.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var imgSplash: ImageView
    private lateinit var progressSplash: ProgressBar
    private lateinit var txtAppName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgSplash = view.findViewById(R.id.imgSplash)
        progressSplash = view.findViewById(R.id.progressSplash)
        txtAppName = view.findViewById(R.id.txtAppName)

        // Observa los cambios del flujo de la URL
        lifecycleScope.launch {
            viewModel.imageUrl.collect { url ->
                url?.let {
                    imgSplash.load(it) {
                        crossfade(true)
                        crossfade(1000)
                        listener(
                            onSuccess = { _, _ -> progressSplash.visibility = View.GONE },
                            onError = { _, _ ->
                                progressSplash.visibility = View.GONE
                            }
                        )
                    }
                }
            }
        }

        // Redirección con delay
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000)
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }

}