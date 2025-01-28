package com.example.hospitalapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemTypesBinding

class TypesAdapter : RecyclerView.Adapter<TypesAdapter.Holder>() {

    var list: ArrayList<String>? = null
    var onTypeClick: ((String) -> Unit)? = null
    private var selectedPosition: Int = -1 // Default to no selection

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {
        val type = list?.get(position)
        holder.textType.text = type

        if (selectedPosition == position) {
            holder.textType.setBackgroundResource(R.drawable.select_type_style)
        } else {
            holder.textType.setBackgroundResource(R.drawable.types_background)
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            type?.let { onTypeClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    fun setSelectedPosition(position: Int) {
        if (position != selectedPosition && position in 0 until (list?.size ?: 0)) {
            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }



    inner class Holder(private val binding: ItemTypesBinding) : RecyclerView.ViewHolder(binding.root) {
        val textType: TextView = binding.textType
    }
}

