package uz.ruzibekov.mvi_xml_example.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.ruzibekov.mvi_xml_example.R
import uz.ruzibekov.mvi_xml_example.data.model.UserModel

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.DataViewHolder>() {

    var users: List<UserModel> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var ivAvatar: ImageView? = null
        private var tvFirstName: TextView? = null
        private var tvLastName: TextView? = null
        private var tvEmail: TextView? = null

        init {
            ivAvatar = itemView.findViewById(R.id.iv_avatar)
            tvFirstName = itemView.findViewById(R.id.tv_first_name)
            tvLastName = itemView.findViewById(R.id.tv_last_name)
            tvEmail = itemView.findViewById(R.id.tv_email)
        }

        fun bind(user: UserModel) {

            tvFirstName?.text = user.first_name
            tvLastName?.text = user.last_name
            tvEmail?.text = user.email

            ivAvatar?.let {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

}