package com.example.hospitalapp.ui.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.DoctorCallsAdapter
import com.example.hospitalapp.databinding.FragmentDoctorCallsBinding
import com.example.hospitalapp.ui.receptionist.CallsFragmentArgs
import com.example.hospitalapp.ui.receptionist.ReceptionistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CallsFragment : Fragment() {

   private var _binding: FragmentDoctorCallsBinding? = null
   private  val binding get() = _binding!!
    private var date: String = ""
    private var searchJob: Job? = null
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
    private val receptionistViewModel: ReceptionistViewModel by viewModels()
    val doctorCallsAdapter : DoctorCallsAdapter by lazy { DoctorCallsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_doctor_calls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDoctorCallsBinding.bind(view)
        fetchCalls(date)
        observer()
        passData()
    }


    private fun passData(){
        arguments?.let {
            val args = com.example.hospitalapp.ui.doctor.CallsFragmentArgs.fromBundle(it)
            fullName =args.fullName
            type = args.type
            specialist = args.specialist
            gender = args.gender
            birthday = args.birthday
            address = args.address
            status = args.status
            email = args.email
            phone = args.phone
            userId = args.id
        }

    }


    private fun fetchCalls(date: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            receptionistViewModel.getCalls(date)
        }
    }


    private fun observer() {
        receptionistViewModel.callsLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (!data.isNullOrEmpty()) {
                    doctorCallsAdapter.list = ArrayList(data)
                    binding.recyclerCalls.visibility = View.VISIBLE
                    binding.recyclerCalls.adapter = doctorCallsAdapter
                    doctorCallsAdapter.notifyDataSetChanged()
//                    binding.noResultsPlaceholder.visibility = View.GONE
                } else {
                    doctorCallsAdapter.list = arrayListOf()
                    binding.recyclerCalls.visibility = View.GONE
//                    binding.noResultsPlaceholder.visibility = View.VISIBLE
                }
            }
        }
    }







}