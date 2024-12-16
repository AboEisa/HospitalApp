package com.example.hospitalapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemTypesBinding
import com.vitatrack.hospitalsystem.models.DataAll

class TypesAdapter: RecyclerView.Adapter<TypesAdapter.Holder>() {

    var list : ArrayList<DataAll> ?= null
    var onUserClick : OnUserClick ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val binding = ItemTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount(): Int {
      return  list?.size ?: 0
    }



    inner class Holder(val binding: ItemTypesBinding) : RecyclerView.ViewHolder(binding.root) {

     val textType = binding.textType
    }


    interface OnUserClick {
        fun onClick (id : Int)
    }





}