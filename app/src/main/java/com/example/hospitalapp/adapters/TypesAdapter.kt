package com.example.hospitalapp.adapters

import android.content.Context
import android.content.res.Configuration
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

        // Set text color dynamically based on the theme
        val textColor = if (isSystemInDarkMode(holder.itemView.context)) {
            holder.itemView.context.getColor(R.color.dark_mode_white) // Use light text in dark mode
        } else {
            holder.itemView.context.getColor(R.color.black) // Use dark text in light mode
        }
        holder.textType.setTextColor(textColor)

        holder.itemView.setOnClickListener {
            type?.let { onTypeClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(private val binding: ItemTypesBinding) : RecyclerView.ViewHolder(binding.root) {
        val textType: TextView = binding.textType
    }

    // Helper function to check if the system is in dark mode
    private fun isSystemInDarkMode(context: Context): Boolean {
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}
