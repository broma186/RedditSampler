package com.example.redditsampler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.databinding.ActivityPostsBinding

class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityPostsBinding>(this,R.layout.activity_posts)
        setSupportActionBar(binding.toolbar)

        setUpPostsList()
    }

    fun setUpPostsList() {
        val adapter = PostAdapter()
        binding.postList.adapter = adapter
       // subscribeUi(adapter)
    }

   /* private fun subscribeUi(adapter: PostAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
    }*/
}
