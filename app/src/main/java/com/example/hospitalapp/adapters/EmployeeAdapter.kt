import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemEmployeeBinding
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.Holder>() {

     var list: ArrayList<DataAll> ?= null
     var onUserClick: OnUserClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
        holder.apply {
            binding.textName.text = data?.first_name
            binding.textType.text = data?.type
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }



    inner class Holder(val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {




        val textName : TextView = binding.textName
        val textType : TextView = binding.textType


        init {
            itemView.setOnClickListener {
                onUserClick?.onClick(list?.get(adapterPosition)?.id!!)
            }
        }
    }

    interface OnUserClick {
        fun onClick(id: Int)
    }
}
