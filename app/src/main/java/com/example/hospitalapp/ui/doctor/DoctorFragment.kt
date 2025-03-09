package com.example.hospitalapp.ui.doctor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentDoctorBinding
import com.example.hospitalapp.databinding.FragmentHrBinding
import com.example.hospitalapp.ui.hr.HrFragmentArgs
import com.example.hospitalapp.ui.hr.HrFragmentDirections
import com.example.hospitalapp.ui.hr.HrViewModel
import com.example.hospitalapp.ui.receptionist.SelectDoctorFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private val binding get() = _binding!!

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
        return inflater.inflate(R.layout.fragment_doctor_calls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDoctorBinding.bind(view)
        userId = DoctorFragmentArgs.fromBundle(requireArguments()).id
        getData()
        viewAndClicks()
    }

    private fun getData() {
        arguments?.let {
            type = DoctorFragmentArgs.fromBundle(it).type
            fullName = DoctorFragmentArgs.fromBundle(it).fullName
            specialist = DoctorFragmentArgs.fromBundle(it).specialist
            gender = DoctorFragmentArgs.fromBundle(it).gender
            birthday = DoctorFragmentArgs.fromBundle(it).birthday
            address = DoctorFragmentArgs.fromBundle(it).address
            status = DoctorFragmentArgs.fromBundle(it).status
            email = DoctorFragmentArgs.fromBundle(it).email
            phone = DoctorFragmentArgs.fromBundle(it).phone
        }
        binding.userType.text = "Specialist -  $type"
        binding.headName.text = fullName
    }

    private fun viewAndClicks() {
        binding.cardProfile.setOnClickListener {
            findNavController().navigate(
                DoctorFragmentDirections.actionDoctorFragmentToProfileFragment(
                    fullName = fullName,
                    type = type,
                    specialist = specialist,
                    gender = gender,
                    birthday = birthday,
                    address = address,
                    status = status,
                    email = email,
                    phone = phone,
                    id = userId
                )
            )
        }

        binding.cardCalls.setOnClickListener {
            findNavController().navigate(
                DoctorFragmentDirections.actionDoctorFragmentToCallsFragment2(
                    fullName = fullName,
                    type = type,
                    specialist = specialist,
                    gender = gender,
                    birthday = birthday,
                    address = address,
                    status = status,
                    email = email,
                    phone = phone,
                    id = userId
                )
            )
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}