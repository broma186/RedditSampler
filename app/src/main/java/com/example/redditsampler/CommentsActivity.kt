package com.example.redditsampler

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditsampler.adapters.CommentsAdapter
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.data.Comment
import com.example.redditsampler.data.Post
import com.example.redditsampler.databinding.ActivityCommentsBinding
import com.example.redditsampler.databinding.ActivityPostsBinding
import com.google.gson.Gson


class CommentsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentsBinding
    private var permalink: String? = null
    lateinit var adapter : CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        binding = DataBindingUtil.setContentView<ActivityCommentsBinding>(this, R.layout.activity_comments)
        setSupportActionBar(binding.toolbar)

        permalink = intent.getStringExtra(COMMENTS_LINK)

        getComments()
    }

    fun getComments() {
        val authorization =  AUTH_HEADER + getSharedPreferences(REDDIT_STORAGE, Context.MODE_PRIVATE).getString(AUTH_TOKEN, "")
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<List<CommentResponse>> =
                RedditServiceHelper.getComments(authorization, permalink)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    setUpCommentsList(response.body()?.get(1)?.data?.children)
                } else {
                    toast("Failed to get comments")
                }
            }

        }
    }

    fun setUpCommentsList(comments: List<Comment>?) {
        binding.commentList.layoutManager = LinearLayoutManager(this)
        adapter = CommentsAdapter(this, comments)
        binding.commentList.adapter = adapter

    }

}