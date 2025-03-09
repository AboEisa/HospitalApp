package com.example.hospitalapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.adapters.CallsAdapter.OnUserClick
import com.example.hospitalapp.databinding.ItemDoctorCallsBinding
import com.example.hospitalapp.models.CallsData

class DoctorCallsAdapter: RecyclerView.Adapter<DoctorCallsAdapter.Holder>() {

    var list : ArrayList<CallsData> ?= null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
      val binding = ItemDoctorCallsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
       holder.apply {
           textName.text = data?.patient_name
           textDate.text = data?.created_at
       }
    }
    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class Holder(private val binding: ItemDoctorCallsBinding) : RecyclerView.ViewHolder(binding.root) {

        val textName = binding.textName
        val textDate = binding.textDate



    }




}