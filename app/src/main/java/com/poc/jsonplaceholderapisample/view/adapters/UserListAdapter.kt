package com.poc.jsonplaceholderapisample.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.jsonplaceholderapisample.databinding.RowItemUserBinding
import com.poc.jsonplaceholderapisample.model.dto.UserDto
import com.poc.jsonplaceholderapisample.view.listeners.PostViewClickListener

class UserListAdapter(
    val userList: ArrayList<UserDto>,
    val postViewClickListener: PostViewClickListener
) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val rowItemUserBinding =
            RowItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(rowItemUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.rowItemUserBinding.apply {
            tvUserId.text = "User Id: ${userList[position].userId}"
            tvPostCount.text = "Post Count: ${
                userList[position].postCount
            }"
        }
    }

    override fun getItemCount() = userList.size

    inner class UserViewHolder(val rowItemUserBinding: RowItemUserBinding) :
        RecyclerView.ViewHolder(rowItemUserBinding.root) {
        init {
            rowItemUserBinding.cardUserPost.setOnClickListener {
                postViewClickListener.postClicked(userList[adapterPosition].userId)
            }
        }
    }
}