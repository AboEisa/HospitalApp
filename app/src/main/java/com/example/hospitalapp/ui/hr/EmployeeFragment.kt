package com.example.hospitalapp.ui.hr

import EmployeeAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.TypesAdapter

import com.example.hospitalapp.databinding.FragmentEmployeeBinding
import com.example.hospitalapp.utils.Constants.Companion.ANALYSIS
import com.example.hospitalapp.utils.Constants.Companion.DOCTOR
import com.example.hospitalapp.utils.Constants.Companion.HR
import com.example.hospitalapp.utils.Constants.Companion.MANAGER
import com.example.hospitalapp.utils.Constants.Companion.NURSE
import com.example.hospitalapp.utils.Constants.Companion.RECEPTIONIST
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeFragment : Fragment() {

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!
    private val hrViewModel: HrViewModel by viewModels()
    private var type = "All"
    private lateinit var fullName: String
    private lateinit var specialist: String
    private lateinit var gender: String
    private lateinit var birthday: String
    private lateinit var address: String
    private lateinit var status: String
    private lateinit var email: String
    private lateinit var phone: String
    private val adapterEmployee : EmployeeAdapter by lazy { EmployeeAdapter( ) }
    private val adapterTypes : TypesAdapter by lazy { TypesAdapter() }
    private val typesList = ArrayList<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmployeeBinding.bind(view)
        displayUserData()
        observers()
        onClicks()
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

            typesList.add("All")
            typesList.add(DOCTOR)
            typesList.add(NURSE)
            typesList.add(ANALYSIS)
            typesList.add(RECEPTIONIST)
            typesList.add(MANAGER)
            typesList.add(HR)

            adapterTypes.list = typesList
            binding.recyclerTypes.adapter = adapterTypes
            adapterTypes.notifyDataSetChanged()



        }


    }
    private fun observers() {
        hrViewModel.getEmployee(fullName, type)

        hrViewModel.employeeLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (data != null && data.isNotEmpty()) {
                    // Data is available
                    adapterEmployee.list = data as ArrayList<DataAll>
                    binding.recyclerEmployee.adapter = adapterEmployee
                    adapterEmployee.notifyDataSetChanged()
                    binding.recyclerEmployee.visibility = View.VISIBLE
                } else {
                    // Data is empty
                    binding.recyclerEmployee.visibility = View.GONE
                }
            }
        }

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}