package com.example.hospitalapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemSelectdoctorBinding
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser

class SelectDoctorAdapter : RecyclerView.Adapter<SelectDoctorAdapter.Holder>() {

    var list: ArrayList<DataAll>? = null
    var selectedPosition: Int = -1

    // Nullable onUserClick property
    private var onUserClick: OnUserClick? = null

    // Setter function for onUserClick
    fun setOnUserClickListener(onUserClick: OnUserClick) {
        this.onUserClick = onUserClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemSelectdoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.getOrNull(position)
        if (data != null) {
            holder.apply {
                textName.text = data.first_name
                textType.text = data.type
                if (selectedPosition == position) {
                    imageSelect.setImageResource(R.drawable.select)
                } else {
                    imageSelect.setImageResource(R.drawable.radio_button)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class Holder(val binding: ItemSelectdoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val textName = binding.textUserName
        val textType = binding.textType
        val imageSelect = binding.imageSelect

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && position < (list?.size ?: 0)) {
                    selectedPosition = position
                    notifyDataSetChanged()
                    val id = list!![position].id!!
                    onUserClick?.onClick(id)
                }
            }
        }
    }


    interface OnUserClick {
        fun onClick(id: Int)
    }
}





