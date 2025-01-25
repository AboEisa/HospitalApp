package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.CallsAdapter
import com.example.hospitalapp.databinding.FragmentReceptionistBinding
import com.example.hospitalapp.ui.hr.HrFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReceptionistFragment : Fragment() {

    private var _binding: FragmentReceptionistBinding? = null
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
    val receptionistViewModel: ReceptionistViewModel by viewModels()
    private val adapterCalls: CallsAdapter by lazy { CallsAdapter() }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receptionist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReceptionistBinding.bind(view)
        displayData()
        onClicks()
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
        binding.userType.text = type
        binding.headName.text = fullName
        binding.userType.text = specialist
    }



    private fun onClicks(){
        binding.cardCalls.setOnClickListener {
            findNavController().navigate(ReceptionistFragmentDirections.actionReceptionistFragmentToCallsFragment(
            ))
        }
        binding
        binding.cardProfile.setOnClickListener {
            findNavController().navigate(ReceptionistFragmentDirections.actionReceptionistFragmentToProfileFragment(
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

        }
        }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






