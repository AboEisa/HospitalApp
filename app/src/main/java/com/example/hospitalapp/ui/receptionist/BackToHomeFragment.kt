package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.FragmentBackToHomeBinding


class BackToHomeFragment : Fragment() {

   private var _binding: FragmentBackToHomeBinding? = null
    val binding get() = _binding!!
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
        return inflater.inflate(R.layout.fragment_back_to_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBackToHomeBinding.bind(view)
//        displayData()
//        onClicks()

    }


    private fun displayData(){
        arguments?.let {
            fullName = ReceptionistFragmentArgs.fromBundle(it).fullName
            type = ReceptionistFragmentArgs.fromBundle(it).type
            specialist = ReceptionistFragmentArgs.fromBundle(it).specialist
            gender = ReceptionistFragmentArgs.fromBundle(it).gender
            birthday = ReceptionistFragmentArgs.fromBundle(it).birthday
            address = ReceptionistFragmentArgs.fromBundle(it).address
            status = ReceptionistFragmentArgs.fromBundle(it).status
            email = ReceptionistFragmentArgs.fromBundle(it).email
            phone = ReceptionistFragmentArgs.fromBundle(it).phone
            userId = ReceptionistFragmentArgs.fromBundle(it).id
        }


    }


   private fun onClicks() {

        binding.btnHome.setOnClickListener {
            findNavController().navigate(BackToHomeFragmentDirections.actionBackToHomeFragmentToReceptionistFragment(
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
    }


}