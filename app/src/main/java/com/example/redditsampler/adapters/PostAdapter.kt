package com.example.redditsampler.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.redditsampler.data.Post
import com.example.redditsampler.viewmodels.PostViewModel
import com.example.redditsampler.databinding.ListItemPostBinding
import androidx.core.content.ContextCompat.startActivity
import com.example.redditsampler.CommentsActivity


class PostAdapter(private val posts : List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    lateinit var viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        return PostViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        posts[position].let { products ->
            with(holder) {
                bind(products)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(
        private val binding: ListItemPostBinding,
        private var context : Context
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            context = binding.root.context

            binding.commentsLayout.setOnClickListener {
                goToComments()
            }
            binding.postLayout.setOnClickListener {
                openPost()
            }
        }
        fun bind(post: Post) {
            with(binding) {
                viewModel = PostViewModel(post)
                executePendingBindings()
            }
        }

        fun goToComments() {
            val intent = Intent(context, CommentsActivity::class.java)
            startActivity(context, intent, null)
        }

        // Opens the reddit post specified by the post object's link in a browser.
        fun openPost() {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(binding.viewModel.link)
            startActivity(context, i, null)
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

