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
    private val adapterEmployee: EmployeeAdapter by lazy { EmployeeAdapter() }
    private val adapterTypes: TypesAdapter by lazy { TypesAdapter() }
    private val typesList = arrayListOf("All", DOCTOR, NURSE, ANALYSIS, RECEPTIONIST, MANAGER, HR)
    private var type: String = "All"
    private var fullName: String = ""
    private lateinit var specialist: String
    private lateinit var gender: String
    private lateinit var birthday: String
    private lateinit var address: String
    private lateinit var status: String
    private lateinit var email: String
    private lateinit var phone: String



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmployeeBinding.bind(view)
        setupTypeAdapter()
        observers()
        fetchEmployees(type, fullName)
        onClicks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

   private fun onClicks(){
        binding.apply {
            binding.btnBack.setOnClickListener{
               findNavController().popBackStack()
            }
        }

    }


    private fun setupTypeAdapter() {
        adapterTypes.list = typesList
        binding.recyclerTypes.adapter = adapterTypes
        adapterTypes.notifyDataSetChanged()


        adapterTypes.onTypeClick = { type ->
            this.type = type
            fetchEmployees(type, fullName)
        }
    }

    private fun fetchEmployees(type: String, fullName: String) {
        hrViewModel.getEmployee(type, fullName)
    }

    private fun observers() {
        hrViewModel.employeeLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (!data.isNullOrEmpty()) {
                    adapterEmployee.list = ArrayList(data)
                    binding.recyclerEmployee.adapter = adapterEmployee
                    adapterEmployee.notifyDataSetChanged()
                } else {
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


