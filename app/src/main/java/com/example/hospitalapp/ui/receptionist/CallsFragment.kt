package com.example.hospitalapp.ui.receptionist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.CallsAdapter
import com.example.hospitalapp.databinding.FragmentCallsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CallsFragment : Fragment() {

    private var _binding: FragmentCallsBinding? = null
    val binding get() = _binding!!
    private val receptionistViewModel: ReceptionistViewModel by viewModels()
    private val adapterCalls: CallsAdapter by lazy { CallsAdapter() }
    private var date: String = "2021-05-09"
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCallsBinding.bind(view)
        fetchCalls(date)
        observer()
        onClicks()
    }


    fun fetchCalls(date: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            receptionistViewModel.getCalls(date)
        }
    }

    fun observer() {
        receptionistViewModel.callsLiveData.observe(viewLifecycleOwner) { response ->
            response?.let {
                val data = it.data
                if (!data.isNullOrEmpty()) {
                    adapterCalls.list = ArrayList(data)
                    binding.recyclerCalls.visibility = View.VISIBLE
                    binding.recyclerCalls.adapter = adapterCalls
                    adapterCalls.notifyDataSetChanged()

                } else {
                    adapterCalls.list = arrayListOf()
                    binding.recyclerCalls.visibility = View.GONE

                }

            }
        }
    }


    fun onClicks() {
        binding.apply {
            addCall.setOnClickListener {
                findNavController().navigate(CallsFragmentDirections.actionCallsFragmentToCreateCallFragment())
            }
            binding.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            dateBacker.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date of Birth")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

                datePicker.show(parentFragmentManager, "DATE_PICKER")

                datePicker.addOnPositiveButtonClickListener { selection ->
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val formattedDate = simpleDateFormat.format(Date(selection))
                    textDate.text = formattedDate
                }
            }

            textDate.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        date = s.toString().trim()
                        fetchCalls(date)
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

        }
    }
}





