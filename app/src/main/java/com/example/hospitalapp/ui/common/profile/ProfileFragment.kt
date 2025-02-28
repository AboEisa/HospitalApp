package com.example.hospitalapp.ui.common.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentProfileBinding
import com.example.hospitalapp.models.Data
import com.example.hospitalapp.models.UserModel
import com.example.hospitalapp.ui.hr.HrViewModel
import com.example.hospitalapp.ui.receptionist.ReceptionistViewModel
import com.vitatrack.hospitalsystem.models.ModelAllUser
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
    private var userId: Int = 0
    private val profileViewModel: ProfileViewModel by viewModels()
    private val receptionistViewModel: ReceptionistViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        val id = ProfileFragmentArgs.fromBundle(requireArguments()).id
        profileViewModel.getProfile(id)
        onClicks()
        displayUserData()
        observer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    private fun onClicks(){

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
//            btnLogout.setOnClickListener {
//                val id = ProfileFragmentArgs.fromBundle(requireArguments()).id ?: 0
//                receptionistViewModel.logoutCall(id)
//
//                // Navigate to LoginFragment and clear the back stack
//                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment(id)
//                findNavController().navigate(action) {
//                    popUpTo(R.id.splashFragment) { inclusive = true }
//                }
//
//                // Show Toast message
//                Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show()
//            }



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
        binding.textName.text = fullName
        binding.firstName.text = "Specialist -  $type"
        binding.gender.text = gender
        binding.birthday.text = birthday
        binding.address.text = address
        binding.status.text = status
        binding.email.text = email
        binding.phone.text = phone
    }

   private fun observer(){
       val id = ProfileFragmentArgs.fromBundle(requireArguments()).id
       profileViewModel.getProfile(id)
        profileViewModel.profileLiveData.observe(viewLifecycleOwner){response->
            response?.let {
                val data = it.data
                val fullName = data.first_name + " " + data.last_name
                binding.apply {
                    textName.text = fullName
                    firstName.text = data.specialist
                    gender.text = data.gender
                    birthday.text = data.birthday
                    address.text = data.address
                    status.text = data.status
                    email.text = data.email
                    phone.text = data.mobile
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }







}