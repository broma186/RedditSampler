package com.example.redditsampler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.Post
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.databinding.ActivityPostsBinding
import com.example.redditsampler.viewmodels.PostViewModel
import kotlinx.coroutines.*
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response

class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding
    lateinit var viewModel: PostViewModel
    lateinit var adapter : PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityPostsBinding>(this, R.layout.activity_posts)
        setSupportActionBar(binding.toolbar)

        adapter = PostAdapter()
        binding.postList.adapter = adapter

        observeList()
    }

    fun observeList() {
        viewModel.productList.observe(viewLifecycleOwner) {result ->
            if (!result.isNullOrEmpty()) {
                binding.noProducts.visibility = View.GONE
            }
            adapter.submitList(result)
        }
    }
}
