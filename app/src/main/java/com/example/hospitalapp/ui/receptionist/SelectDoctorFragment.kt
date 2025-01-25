package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.SelectDoctorAdapter
import com.example.hospitalapp.databinding.FragmentSelectDoctorBinding
import com.vitatrack.hospitalsystem.models.DataAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectDoctorFragment : Fragment() {

    private var _binding: FragmentSelectDoctorBinding? = null
    private val binding get() = _binding!!

    private val receptionistViewModel: ReceptionistViewModel by viewModels()
    private val adapterDoctor: SelectDoctorAdapter by lazy { SelectDoctorAdapter() }
    private var type: String = "doctor"
    private var fullName: String = ""
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectDoctorBinding.bind(view)
        fetchDoctors(type, fullName)
        observer()
        onUserClick()
        onClicks()
    }

    private fun onUserClick() {
        adapterDoctor.setOnUserClickListener(object : SelectDoctorAdapter.OnUserClick {
            override fun onClick(id: Int) {
                val selectedDoctor = adapterDoctor.list?.find { it.id == id }
                if (selectedDoctor != null) {
                    val selectedDoctorName = selectedDoctor.first_name


                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        "selectedDoctorId", id
                    )
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        "selectedDoctorName", selectedDoctorName
                    )
                }
            }
        })
        binding.selectDoctor.adapter = adapterDoctor
    }




    private fun onClicks() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.textsearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(200)
                    fullName = s.toString().trim()
                    fetchDoctors(type, fullName)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSelectDoctor.setOnClickListener {
            // Retrieve the doctor ID and name from savedStateHandle
            val selectedDoctorName = findNavController().previousBackStackEntry?.savedStateHandle?.get<String>("selectedDoctorName")
            val selectedDoctorId = findNavController().previousBackStackEntry?.savedStateHandle?.get<Int>("selectedDoctorId")

            // If a doctor is selected, pass the information back and navigate
            if (selectedDoctorId != null && selectedDoctorName != null) {
                // You can pass these values back to the previous fragment if needed
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "selectedDoctorId", selectedDoctorId
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "selectedDoctorName", selectedDoctorName
                )

                // Navigate back to the previous fragment
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Please select a doctor", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun fetchDoctors(type: String, fullName: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            receptionistViewModel.selectDoctor(type, fullName)
        }
    }

    private fun observer() {
        receptionistViewModel.selectDoctorLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (!data.isNullOrEmpty()) {
                    adapterDoctor.list = data as ArrayList<DataAll>?
                    binding.selectDoctor.visibility = View.VISIBLE
                    binding.noResultsPlaceholder.visibility = View.GONE
                    adapterDoctor.notifyDataSetChanged()
                } else {
                    binding.selectDoctor.visibility = View.GONE
                    binding.noResultsPlaceholder.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

