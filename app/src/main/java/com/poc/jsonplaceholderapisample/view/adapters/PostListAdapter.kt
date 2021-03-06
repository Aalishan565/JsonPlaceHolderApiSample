package com.poc.jsonplaceholderapisample.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.jsonplaceholderapisample.databinding.RowItemPostBinding
import com.poc.jsonplaceholderapisample.model.dto.PostDto

class PostListAdapter(
    private val userPostList: ArrayList<PostDto>
) :
    RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val rowItemPostBinding =
            RowItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(rowItemPostBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.rowItemPostBinding.apply {
            tvPostTitle.text = "Title:\n ${userPostList[position].title}"
            tvPostBody.text = "Body:\n ${userPostList[position].body}"
        }
    }

    override fun getItemCount() = userPostList.size

    inner class PostViewHolder(val rowItemPostBinding: RowItemPostBinding) :
        RecyclerView.ViewHolder(rowItemPostBinding.root)
}