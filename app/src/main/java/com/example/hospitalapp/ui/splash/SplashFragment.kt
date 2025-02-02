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

   private fun splashProgress(){

        progressView.apply {
            visibility = View.VISIBLE
            setProgress(0f)
        }
        Handler(Looper.getMainLooper()).apply {
            postDelayed({ progressView.setProgress(50f) }, 500)
            postDelayed({ progressView.setProgress(80f) }, 1000)
            postDelayed({
                progressView.setProgress(100f)
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment(id = userId)
                )
            }, 2000)
        }
    }
}