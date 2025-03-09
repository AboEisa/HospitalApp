package com.example.hospitalapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentSplashBinding
import com.example.hospitalapp.utils.CustomProgressBar

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private var userId: Int = 0
    private lateinit var progressView: CustomProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSplashBinding.bind(view)
        progressView = binding.progressView
        userId = SplashFragmentArgs.fromBundle(requireArguments()).id
        splashProgress()
    }

    private fun splashProgress() {
        progressView.apply {
            visibility = View.VISIBLE
            setProgress(0f)
        }
        val totalDuration = 2000L // Total duration in milliseconds
        val stepDuration = 100L   // Duration between each progress update
        val totalSteps = totalDuration / stepDuration // Total steps to reach 100%
        val progressIncrement = 100f / totalSteps // Progress increment per step
        var currentStep = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (currentStep <= totalSteps) {
                    val progress = currentStep * progressIncrement
                    progressView.setProgress(progress)
                    currentStep++
                    handler.postDelayed(this, stepDuration)
                } else {
                    // Progress complete, navigate to the next screen
                    if (isAdded) { // Check if the fragment is still attached
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToLoginFragment(id = userId)
                        )
                    }
                }
            }
        }

        handler.post(runnable) // Start the progress updates
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}