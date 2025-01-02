package com.example.hospitalapp.ui.hr

import EmployeeAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private var id: Int = 0
    private var searchJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmployeeBinding.bind(view)
        setupTypeAdapter()
        observer()
        fetchEmployees(type, fullName)
        onClicks()
    }

    private fun onClicks() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            addEmployee.setOnClickListener {
                findNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToNewUserFragment())
            }
            binding.textSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(200)
                        fullName = s.toString().trim()
                        fetchEmployees(type, fullName)
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
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
       lifecycleScope.launch(Dispatchers.IO) {
           hrViewModel.getEmployee(type, fullName)
       }
    }


    private fun observer() {
        hrViewModel.employeeLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (!data.isNullOrEmpty()) {
                    // Set the list to the adapter
                    adapterEmployee.list = ArrayList(data)
                    binding.recyclerEmployee.visibility = View.VISIBLE
                    binding.recyclerEmployee.adapter = adapterEmployee
                    adapterEmployee.notifyDataSetChanged()
                    binding.noResultsPlaceholder.visibility = View.GONE
                } else {
                    adapterEmployee.list = arrayListOf()
                    binding.recyclerEmployee.visibility = View.GONE
                    binding.noResultsPlaceholder.visibility = View.VISIBLE
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


