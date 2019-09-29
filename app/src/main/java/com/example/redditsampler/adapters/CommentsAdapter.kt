package com.example.redditsampler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.redditsampler.R
import com.example.redditsampler.data.Comment
import com.example.redditsampler.databinding.ListItemCommentBinding
import com.example.redditsampler.viewmodels.CommentViewModel

class CommentsAdapter(private val context: Context, private val comments : List<Comment>?) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.CommentViewHolder {
        return CommentViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_comment, parent, false)
            ,parent.context)
    }

    override fun onBindViewHolder(holder: CommentsAdapter.CommentViewHolder, position: Int) {
        comments!![position].let { comments ->
            with(holder) {
                bind(comments)
            }
        }
    }

    override fun getItemCount(): Int {
        return  comments?.size!!
    }

    class CommentViewHolder(
        private val binding: ListItemCommentBinding,
        private var context : Context
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            context = binding.root.context
        }
        fun bind(comment: Comment) {
            with(binding) {
                viewModel = CommentViewModel(comment)
                executePendingBindings()
            }
        }

    }
}