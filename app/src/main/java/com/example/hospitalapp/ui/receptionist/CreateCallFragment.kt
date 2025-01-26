package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentCreateCallBinding
import com.example.hospitalapp.ui.hr.HrFragmentArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateCallFragment : Fragment() {

    private var _binding: FragmentCreateCallBinding? = null
    private val binding get() = _binding!!
   private val receptionistViewModel: ReceptionistViewModel by viewModels()
    private lateinit var type: String
    private lateinit var fullName: String
    private lateinit var specialist: String
    private lateinit var gender: String
    private lateinit var birthday: String
    private lateinit var address: String
    private lateinit var status: String
    private lateinit var email: String
    private lateinit var phone: String
    private var userId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_call, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateCallBinding.bind(view)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("selectedDoctorName")
            ?.observe(viewLifecycleOwner) { doctorName ->
                binding.selectDoctor.setText(doctorName) // Display the selected doctor's name in the textbox
            }
        passData()
        onClicks()

    }


    private fun passData(){
        arguments?.let {
            fullName = CreateCallFragmentArgs.fromBundle(it).fullName
            type = CreateCallFragmentArgs.fromBundle(it).type
            specialist = CreateCallFragmentArgs.fromBundle(it).specialist
            gender = CreateCallFragmentArgs.fromBundle(it).gender
            birthday = CreateCallFragmentArgs.fromBundle(it).birthday
            address = CreateCallFragmentArgs.fromBundle(it).address
            status = CreateCallFragmentArgs.fromBundle(it).status
            email = CreateCallFragmentArgs.fromBundle(it).email
            phone = CreateCallFragmentArgs.fromBundle(it).phone
            userId = CreateCallFragmentArgs.fromBundle(it).id
        }
    }



   private fun validate(): Boolean {

        binding.apply {
            val patientName = textPatientName.text.toString().trim()
            val age = textAge.text.toString().trim()
            val phoneNumber = textPhoneNumber.text.toString().trim()
            val description = caseDescription.text.toString().trim()

            when {
                patientName.isEmpty() -> {
                    textPatientName.error = getString(R.string.patient_name)
                    return false
                }

                age.isEmpty() -> {
                    textAge.error = getString(R.string.age)
                    return false
                }

                phoneNumber.isEmpty() -> {
                    textPhoneNumber.error = getString(R.string.phone_number)
                    return false
                }

                description.isEmpty() -> {
                    caseDescription.error = getString(R.string.case_description)
                    return false
                }

                else -> return true


            }
            return false
        }
    }

    private fun onClicks() {
        binding.selectDoctor.setOnClickListener {
            findNavController().navigate(CreateCallFragmentDirections.actionCreateCallFragmentToSelectDoctorFragment())
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCreateCall.setOnClickListener {
            if (validate()) {
                binding.apply {
                    val patientName = textPatientName.text.toString().trim()
                    val age = textAge.text.toString().trim()
                    val phoneNumber = textPhoneNumber.text.toString().trim()
                    val description = caseDescription.text.toString().trim()
                    val selectedDoctorId = findNavController().currentBackStackEntry?.savedStateHandle?.get<Int>("selectedDoctorId")

                    if (selectedDoctorId != null) {
                        createCall(patientName, age, phoneNumber, selectedDoctorId, description)
                        textPatientName.text?.clear()
                        textAge.text?.clear()
                        textPhoneNumber.text?.clear()
                        caseDescription.text?.clear()
                        findNavController().navigate(CreateCallFragmentDirections.actionCreateCallFragmentToBackToHomeFragment(
                            id = userId,
                            fullName = fullName,
                            type = type,
                            specialist = specialist,
                            gender = gender,
                            birthday = birthday,
                            address = address,
                            status = status,
                            email = email,
                            phone = phone
                        ))
                    } else {
                        Toast.makeText(requireContext(), "Doctor not selected", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    private fun createCall(
        patientName: String,
        age: String,
        phoneNumber: String,
        doctorId: Int,
        description: String
    ) {
        receptionistViewModel.createCall(
            patientName,
            age,
            doctorId,
            phoneNumber,
            description
        )
    }
}





