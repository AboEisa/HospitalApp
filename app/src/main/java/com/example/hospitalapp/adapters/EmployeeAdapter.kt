import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hospitalapp.R
import com.example.hospitalapp.databinding.ItemEmployeeBinding
import com.vitatrack.hospitalsystem.models.DataAll

class EmployeeAdapter( ) : RecyclerView.Adapter<EmployeeAdapter.Holder>() {
    var list: ArrayList<DataAll> ?= null
    var onUserClick: OnUserClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
        if (data != null) {
            // Split full name into firstName and lastName
            val nameParts = data.first_name.split(" ", limit = 2)
            val firstName = nameParts.firstOrNull() ?: ""
            val lastName = if (nameParts.size > 1) nameParts.drop(1).joinToString(" ") else ""

            // Set name
            holder.textName.text = "$firstName $lastName"
            holder.textType.text = data.type

            // Load avatar image using Glide (you can replace this with Picasso if preferred)
            Glide.with(holder.image.context)
                .load(data.avatar)
                .placeholder(R.drawable.useric_user) // Default image if the avatar is not available
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        val textName = binding.textUserName
        val textType = binding.textType
        val image = binding.image

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

    // You can add a method to update the list if needed
    fun updateData(newList: ArrayList<DataAll>?) {
        list = newList
        notifyDataSetChanged()
    }
}
