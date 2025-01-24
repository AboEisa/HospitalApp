package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentReceptionistBinding


class ReceptionistFragment : Fragment() {

    private val _binding: FragmentReceptionistBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receptionist, container, false)
    }

    private fun onClicks(){
        binding.cardCalls.setOnClickListener {
            findNavController().navigate(ReceptionistFragmentDirections.actionReceptionistFragmentToCallsFragment(


            ))
        }
    }






}