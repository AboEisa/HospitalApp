package com.example.hospitalapp.ui.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.ApiServices
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var apiServices: ApiServices

    private val loginViewModel: LoginViewModel by viewModels()
    private var userId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        userId = LoginFragmentArgs.fromBundle(requireArguments()).id
        onClicks()
        observer()
    }

    private fun onClicks() {
        binding.btnLogin.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val email = binding.textEmail.text.toString().trim()
        val password = binding.textPassword.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.textEmail.error = getString(R.string.e_mail)
            }
            password.isEmpty() -> {
                binding.textPassword.error = getString(R.string.password)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.textEmail.error = getString(R.string.e_mail)
            }
            else -> {
                lifecycleScope.launch {
                    try {
                        val response = apiServices.login(email, password, Constants.BEARER_TOKEN)
                        if (response.status == 1) {
                            fetchDataOfUser(email, password)
                        } else {
                            when (response.message) {
                                "Unauthorized" -> {
                                    Toast.makeText(requireContext(), getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), getString(R.string.api_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun fetchDataOfUser(email: String, password: String){
        lifecycleScope.launch(Dispatchers.IO) {
            loginViewModel.login(email, password,Constants.BEARER_TOKEN)
        }
    }

    private fun observer() {
        loginViewModel.loginLiveData.observe(viewLifecycleOwner) { data ->
            if (view == null) return@observe
            if (data != null && data.status == 1) {
                val responseData = data.data
                responseData.let {
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
                phone = phone,
                id = userId


            )
//            DOCTOR -> LoginFragmentDirections.actionLoginFragmentToDoctorFragment(
//               fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,
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
            RECEPTIONIST -> LoginFragmentDirections.actionLoginFragmentToReceptionistFragment(
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
//            ANALYSIS -> LoginFragmentDirections.actionLoginFragmentToAnalysisFragment(
//                fullName = fullName,
//                type = type,
//                specialist = specialist,
//                gender = gender,
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
//                gender = gender,
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
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
