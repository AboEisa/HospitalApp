package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentCaseDetailsBinding


class CaseDetailsFragment : Fragment() {

    private var _binding : FragmentCaseDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReceptionistViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCaseDetailsBinding.bind(view)
    }




    fun observe(){

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}