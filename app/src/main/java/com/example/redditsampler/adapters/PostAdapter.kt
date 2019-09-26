package com.example.redditsampler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditsampler.viewmodels.PostViewModel
import com.example.redditsampler.databinding.ListItemPostBinding

class PostAdapter : ListAdapter<PostViewModel, RecyclerView.ViewHolder>(PostDiffCallback()){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        (holder as PostViewHolder).bind(post)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            ListItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    class PostViewHolder(
        private val binding: ListItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
           /* binding.setClickListener {

            }*/
        }

        fun bind(item: PostViewModel) {
            binding.apply {
                post = item
                executePendingBindings()
            }
        }
    }

    private class PostDiffCallback : DiffUtil.ItemCallback<PostViewModel>() {

        override fun areItemsTheSame(oldItem: PostViewModel, newItem: PostViewModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: PostViewModel, newItem: PostViewModel): Boolean {
            return oldItem == newItem
        }
    }
}

