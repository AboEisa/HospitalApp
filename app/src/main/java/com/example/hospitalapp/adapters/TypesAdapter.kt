package com.example.hospitalapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemTypesBinding
import com.example.hospitalapp.models.Data
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser

class TypesAdapter: RecyclerView.Adapter<TypesAdapter.Holder>() {

    var list : ArrayList<String> ?= null
    private var onUserClick : OnUserClick ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val binding = ItemTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.textType.text = list?.get(position)
        holder.itemView.setOnClickListener {
            onUserClick?.onClick(position)
        }
    }

    override fun getItemCount(): Int {
      return  list?.size ?: 0
    }

    inner class Holder(private val binding: ItemTypesBinding) : RecyclerView.ViewHolder(binding.root) {

     val textType = binding.textType

    }
    interface OnUserClick {
        fun onClick (id : Int)
    }





}