package com.example.hospitalapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemCallBinding
import com.example.hospitalapp.models.CallsData

class CallsAdapter: RecyclerView.Adapter<CallsAdapter.Holder>() {


    var list : ArrayList<CallsData> ?= null
    var onUserClick : OnUserClick ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }



    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)

        holder.apply {

            holder.textName.text = data?.patient_name
            holder.textDate.text = data?.created_at


            if (data?.status =="logout"){
                holder.imageCondition.setImageResource(R.drawable.done)
            }else{
                holder.imageCondition.setImageResource(R.drawable.pending)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class Holder(val binding: ItemCallBinding) : RecyclerView.ViewHolder(binding.root) {
        val textName = binding.textName
        val textDate = binding.textDate
        val imageCondition = binding.icCondation
        init {
            itemView.setOnClickListener {
                val id = list?.get(layoutPosition)?.id
                id?.let {
                    onUserClick?.onClick(id)
                }
            }
        }


    }
    interface OnUserClick {
        fun onClick (id : Int)
    }

}