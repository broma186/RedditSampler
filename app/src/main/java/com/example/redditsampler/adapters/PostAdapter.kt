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


class PostAdapter(private val posts : List<Post>?) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        return PostViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_post, parent, false)
        ,parent.context)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        Log.d("TEST", "on bind")
        posts!![position].let { products ->
            with(holder) {
                Log.d("TEST", "on bind with holder")
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

            binding.commentsLayout.setOnClickListener {
                goToComments()
            }
            binding.postLayout.setOnClickListener {
                openPost()
            }
        }
        fun bind(post: Post) {
            Log.d("TEST", "on bind list item 1")

            with(binding) {
                Log.d("TEST", "on bind list item 2")

                viewModel = PostViewModel(post)
                executePendingBindings()
                Log.d("TEST", "on bind list item 3")

            }
        }

        fun goToComments() {
            val intent = Intent(context, CommentsActivity::class.java)
            startActivity(context, intent, null)
        }

        // Opens the reddit post specified by the post object's link in a browser.
        fun openPost() {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(binding.viewModel?.link)
            startActivity(context, i, null)
        }
    }
}

