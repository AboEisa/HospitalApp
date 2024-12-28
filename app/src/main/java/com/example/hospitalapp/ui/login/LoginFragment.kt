package com.example.hospitalapp.ui.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentLoginBinding
import com.example.hospitalapp.models.Data
import com.example.hospitalapp.utils.Constants
import com.example.hospitalapp.utils.Constants.Companion.ANALYSIS
import com.example.hospitalapp.utils.Constants.Companion.DOCTOR
import com.example.hospitalapp.utils.Constants.Companion.HR
import com.example.hospitalapp.utils.Constants.Companion.MANAGER
import com.example.hospitalapp.utils.Constants.Companion.NURSE
import com.example.hospitalapp.utils.Constants.Companion.RECEPTIONIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
        observeViewModel()
    }

    private fun onClicks() {
        binding.btnLogin.setOnClickListener {
            validation()
        }
    }



    private fun validation() {
        val email = binding.textEmail.text.toString().trim()
        val password = binding.textPassword.text.toString().trim()

        // Input validation
        if (email.isEmpty()) {
            binding.textEmail.error = getString(R.string.e_mail)
        } else if (password.isEmpty()) {
            binding.textPassword.error = getString(R.string.password)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textEmail.error = getString(R.string.e_mail)
        } else {
            // Perform login without Firebase token (direct login request to backend)
            loginViewModel.login(email, password,Constants.BEARER_TOKEN)
        }
    }




    private fun observeViewModel() {
        loginViewModel.loginLiveData.observe(viewLifecycleOwner) { data ->
            if (data != null && data.status == 1) {
                val responseData = data.data
                responseData?.let {
                    navigateUserToHome(
                        it.type
                        , it.first_name
                        , it.last_name
                        , it.specialist
                        , it.gender
                        , it.birthday
                        , it.address
                        , it.status
                        , it.email
                        ,it.mobile
                    )
                }
            } else {
                binding.textPassword.error = "Login failed"
            }
        }
    }


    private fun navigateUserToHome(
        type: String,
        firstName: String,
        lastName: String,
        specialist: String,
        gender: String,
        birthday: String,
        address: String,
        status: String,
        email: String,
        phone: String
    ) {
        val fullName = "$firstName $lastName"
        val action = when (type) {
            HR -> LoginFragmentDirections.actionLoginFragmentToHrFragment(
                fullName = fullName,
                type = type,
                specialist = specialist,
                gender = gender,
                birthday = birthday,
                address = address,
                status = status,
                email = email,
                phone = phone
            )
//            DOCTOR -> LoginFragmentDirections.actionLoginFragmentToDoctorFragment(
//               fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,  // Add a comma here
//                birthday = birthday,
//                address = address,
//                status = status,
//                email = email,
//                phone = phone
//            )
//            NURSE -> LoginFragmentDirections.actionLoginFragmentToNurseFragment(
//                fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,  // Add a comma here
//                birthday = birthday,
//                address = address,
//                status = status,
//                email = email,
//                phone = phone
//            )
//            RECEPTIONIST -> LoginFragmentDirections.actionLoginFragmentToReceptionistFragment(
//                fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,  // Add a comma here
//                birthday = birthday,
//                address = address,
//                status = status,
//                email = email,
//                phone = phone
//            )
//            ANALYSIS -> LoginFragmentDirections.actionLoginFragmentToAnalysisFragment(
//                fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,  // Add a comma here
//                birthday = birthday,
//                address = address,
//                status = status,
//                email = email,
//                phone = phone
//            )
//            MANAGER -> LoginFragmentDirections.actionLoginFragmentToManagerFragment(
//                fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,  // Add a comma here
//                birthday = birthday,
//                address = address,
//                status = status,
//                email = email,
//                phone = phone
//            )

            else -> {
                Toast.makeText(requireContext(), "Unknown user type", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Navigate to the target fragment
        findNavController().navigate(action)
    }













    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
