package com.example.hospitalapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemTypesBinding
import com.example.hospitalapp.models.Data

class TypesAdapter : RecyclerView.Adapter<TypesAdapter.Holder>() {

    var list: ArrayList<String>? = null
    var onTypeClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val type = list?.get(position)
        holder.textType.text = type
        holder.itemView.setOnClickListener {
            type?.let { onTypeClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(private val binding: ItemTypesBinding) : RecyclerView.ViewHolder(binding.root) {
        val textType: TextView = binding.textType
    }
}
