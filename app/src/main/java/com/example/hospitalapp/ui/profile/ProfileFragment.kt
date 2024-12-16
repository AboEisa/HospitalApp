package com.example.hospitalapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var fullName: String
    private lateinit var type: String
    private lateinit var specialist: String
    private lateinit var gender: String
    private lateinit var birthday: String
    private lateinit var address: String
    private lateinit var status: String
    private lateinit var email: String
    private lateinit var phone: String




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        onClicks()
        displayUserData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    private fun onClicks(){

        binding.apply {
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun displayUserData() {
        arguments?.let {
            fullName = ProfileFragmentArgs.fromBundle(it).fullName
            type = ProfileFragmentArgs.fromBundle(it).type
            specialist = ProfileFragmentArgs.fromBundle(it).specialist
            gender = ProfileFragmentArgs.fromBundle(it).gender
            birthday = ProfileFragmentArgs.fromBundle(it).birthday
            address = ProfileFragmentArgs.fromBundle(it).address
            status = ProfileFragmentArgs.fromBundle(it).status
            email = ProfileFragmentArgs.fromBundle(it).email
            phone = ProfileFragmentArgs.fromBundle(it).phone
        }
        // this data in ui
        binding.textName.text = fullName
        binding.firstName.text = specialist
        binding.gender.text = gender
        binding.birthday.text = birthday
        binding.address.text = address
        binding.status.text = status
        binding.email.text = email
        binding.phone.text = phone
    }






}