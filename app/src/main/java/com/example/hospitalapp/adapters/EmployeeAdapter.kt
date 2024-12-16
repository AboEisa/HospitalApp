import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalapp.databinding.ItemEmployeeBinding
import com.vitatrack.hospitalsystem.models.DataAll
import com.vitatrack.hospitalsystem.models.ModelAllUser

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.Holder>() {

     var list: ArrayList<DataAll> ?= null
    private var onUserClick: OnUserClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {

            textName.text = list?.get(position)?.first_name
            textType.text = list?.get(position)?.type
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }



    inner class Holder(val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onUserClick?.onClick(list?.get(adapterPosition)?.id!!)
            }
        }
    }

    interface OnUserClick {
        fun onClick(id: Int)
    }
}
