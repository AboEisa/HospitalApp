package com.example.hospitalapp.ui.hr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentHrBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HrFragment : Fragment() {

    private var _binding: FragmentHrBinding? = null
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
    val hrvViewModel: HrViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHrBinding.bind(view)
        userId = HrFragmentArgs.fromBundle(requireArguments()).id
        passData()
        viewAndClicks()
    }

    private fun passData(){
        arguments?.let {
            fullName = HrFragmentArgs.fromBundle(it).fullName
            type = HrFragmentArgs.fromBundle(it).type
            specialist = HrFragmentArgs.fromBundle(it).specialist
            gender = HrFragmentArgs.fromBundle(it).gender
            birthday = HrFragmentArgs.fromBundle(it).birthday
            address = HrFragmentArgs.fromBundle(it).address
            status = HrFragmentArgs.fromBundle(it).status
            email = HrFragmentArgs.fromBundle(it).email
            phone = HrFragmentArgs.fromBundle(it).phone
        }
        binding.userType.text = type
        binding.headName.text = fullName
        binding.userType.text = specialist
    }

    private fun viewAndClicks() {
        binding.cardProfile.setOnClickListener {
            findNavController().navigate(HrFragmentDirections.actionHrFragmentToProfileFragment(
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
            ))
        }
        binding.cardEmployee.setOnClickListener {
            findNavController().navigate(HrFragmentDirections.actionHrFragmentToEmployeeFragment(
                fullName = fullName,
                type = type,
                specialist = specialist,
                gender = gender,
                birthday = birthday,
                address = address,
                status = status,
                email = email,
                phone = phone,
                id = userId.toString()
            ))


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}