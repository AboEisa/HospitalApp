package com.example.hospitalapp.ui.hr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentNewUserBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class NewUserFragment : Fragment() {

    private var _binding: FragmentNewUserBinding? = null
    private val binding get() = _binding!!
    private val newUserViewModel: NewUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewUserBinding.bind(view)
        onClicks()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    private fun validation(): Boolean {
        binding.apply {
            val fName = textFirstName.text.toString().trim()
            val lName = textSecondName.text.toString().trim()
            val email = email.text.toString().trim()
            val password = textPassword.text.toString().trim()
            val address = textAddress.text.toString().trim()
            val phone = phone.text.toString().trim()
            val gender = spinnerGender.selectedItem.toString()
            val type = spinnerSpecialist.selectedItem.toString()
            val status = spinnerStatus.selectedItem.toString()

            when {
                fName.isEmpty() -> {
                    textFirstName.error = getString(R.string.first_name)
                    return false
                }

                lName.isEmpty() -> {
                    textSecondName.error = getString(R.string.last_name)
                    return false
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    this.email.error = getString(R.string.e_mail)
                    return false
                }

                password.isEmpty() -> {
                    textPassword.error = getString(R.string.password)
                    return false
                }

                address.isEmpty() -> {
                    textAddress.error = getString(R.string.address)
                    return false
                }

                phone.isEmpty() -> {
                    this.phone.error = getString(R.string.phone_numberr)
                    return false
                }

                gender == getString(R.string.gender) -> {
                    Toast.makeText(requireContext(), getString(R.string.gender), Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                type == getString(R.string.type) -> {
                    Toast.makeText(requireContext(), getString(R.string.type), Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                status == getString(R.string.status) -> {
                    Toast.makeText(requireContext(), getString(R.string.status), Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                else -> return true
            }
        }
    }

    private fun onClicks() {
        binding.apply {
            btnCreateUser.setOnClickListener {
                if (validation()) {
                    val fName = textFirstName.text.toString().trim()
                    val lName = textSecondName.text.toString().trim()
                    val email = email.text.toString().trim()
                    val password = textPassword.text.toString().trim()
                    val address = textAddress.text.toString()
                    val phone = phone.text.toString().trim()
                    val gender = spinnerGender.selectedItem.toString()
                    val type = spinnerSpecialist.selectedItem.toString()
                    val status = spinnerStatus.selectedItem.toString()
                    val birthday = birthday.text.toString().trim()
                    newUserViewModel.register(
                        email,
                        password,
                        fName,
                        lName,
                        gender,
                        type,
                        birthday,
                        status,
                        address,
                        phone,
                        type
                    )
                    Toast.makeText(
                        requireContext(),
                        "User created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            birthday.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date of Birth")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

                datePicker.show(parentFragmentManager, "DATE_PICKER")

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = simpleDateFormat.format(Date(selection))
                    birthday.text = formattedDate
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
