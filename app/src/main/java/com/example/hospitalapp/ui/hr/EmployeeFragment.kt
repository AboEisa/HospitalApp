package com.example.hospitalapp.ui.hr

import EmployeeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R

import com.example.hospitalapp.databinding.FragmentEmployeeBinding
import com.example.hospitalapp.ui.profile.ProfileFragmentArgs
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var type: String
    private lateinit var fullName: String
    private lateinit var specialist: String
    private lateinit var gender: String
    private lateinit var birthday: String
    private lateinit var address: String
    private lateinit var status: String
    private lateinit var email: String
    private lateinit var phone: String
    lateinit var adapter: EmployeeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmployeeBinding.bind(view)
        displayUserData()
        onClicks()
        observe()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    private fun displayUserData() {
        arguments?.let {
            fullName = EmployeeFragmentArgs.fromBundle(it).fullName
            type = EmployeeFragmentArgs.fromBundle(it).type
            specialist = EmployeeFragmentArgs.fromBundle(it).specialist
            gender = EmployeeFragmentArgs.fromBundle(it).gender
            birthday = EmployeeFragmentArgs.fromBundle(it).birthday
            address = EmployeeFragmentArgs.fromBundle(it).address
            status = EmployeeFragmentArgs.fromBundle(it).status
            email = EmployeeFragmentArgs.fromBundle(it).email
            phone = EmployeeFragmentArgs.fromBundle(it).phone
        }

    }


    private fun onClicks() {
        binding.apply {
            addEmployee.setOnClickListener {
                findNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToNewUserFragment())
            }
           btnBack.setOnClickListener{
               findNavController().popBackStack()
           }
        }

    }
   private fun observe() {

        viewModel.employeeLiveData.observe(viewLifecycleOwner){
            val data = it?.data as ModelAllUser
            adapter.list = data.data as ArrayList<DataAll>
            binding.recyclerEmployee.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}