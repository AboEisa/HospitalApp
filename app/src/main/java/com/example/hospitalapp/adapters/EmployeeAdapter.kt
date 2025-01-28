import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemEmployeeBinding
import com.vitatrack.hospitalsystem.models.DataAll


class EmployeeAdapter() : RecyclerView.Adapter<EmployeeAdapter.Holder>() {
    var list: ArrayList<DataAll> ?= null
    var onUserClick: OnUserClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
            holder.textName.text = data?.first_name
            holder.textType.text = "Specialist - ${data?.type}"
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        val textName = binding.textUserName
        val textType = binding.textType

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
        fun onClick(id: Int)
    }
}
