package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentCaseDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaseDetailsFragment : Fragment() {

    private var _binding : FragmentCaseDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReceptionistViewModel by viewModels()
    private var id: Int = 0




    private var status: String? = null




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
        observe()
        fetchCall()
        onClicks()
    }



   private fun fetchCall(){
    val id = CaseDetailsFragmentArgs.fromBundle(requireArguments()).id
    viewModel.showCall(id)
    }


   private fun observe(){
        viewModel.showCallLiveData.observe(viewLifecycleOwner){ response ->
            response?.let {
                val response = response?.data
                if (response != null){
                   binding.PatientInfo.text = response.patient_name
                    binding.AgeInfo.text = "${response.age} Years"
                    binding.PhoneNumberInfo.text = response.phone
                    binding.dateInfo.text = response.created_at
                    binding.textDescription.text = response.description
                }
                if (response?.status =="logout"){
                    binding.statusImageInfo.setImageResource(R.drawable.done)
                    binding.statusInfo.text = "Finished"
                    binding.btnLogout.visibility = View.GONE
                }else{
                    binding.statusImageInfo.setImageResource(R.drawable.pending)
                    binding.statusInfo.text = "Process"
                    binding.btnLogout.visibility = View.VISIBLE
                }



            }
            }
    }

    private fun onClicks() {
        binding.btnLogout.setOnClickListener {
            val id = CaseDetailsFragmentArgs.fromBundle(requireArguments()).id
            viewModel.logoutCall(id)
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}