package com.example.redditsampler.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.redditsampler.data.Post
import com.example.redditsampler.viewmodels.PostViewModel
import com.example.redditsampler.databinding.ListItemPostBinding
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.example.redditsampler.CommentsActivity
import com.example.redditsampler.R
import com.example.redditsampler.utils.*


class PostAdapter(private val posts : List<Post>?) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        return PostViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_post, parent, false)
        ,parent.context)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        posts!![position].let { products ->
            with(holder) {
                bind(products)
            }
        }
    }

    override fun getItemCount(): Int {
        return  posts?.size!!
    }

    class PostViewHolder(
        private val binding: ListItemPostBinding,
        private var context : Context
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            context = binding.root.context

            binding.setClickListener { view ->
                binding.viewModel?.openPost()
            }

            binding.commentsLayout.setOnClickListener {
                binding.viewModel?.goToComments()
            }
        }
        fun bind(post: Post) {
            with(binding) {
                viewModel = PostViewModel(context, post)
                executePendingBindings()
            }
        }
    }
}

