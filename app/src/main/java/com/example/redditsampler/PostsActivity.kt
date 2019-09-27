package com.example.redditsampler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
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

        getPosts()
    }

    fun setUpPostsList(posts : List<Post>?) {
        adapter = PostAdapter(posts)
        binding.postList.adapter = adapter
    }


    fun getPosts()  {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            withContext(Dispatchers.Main) {
                try {
                    setUpPostsList(response.body()?.data?.children)
                } catch (e: HttpException) {
                    toast("\"Exception ${e.message}\"")
                    finish()
                } catch (e: Throwable) {
                    toast("Ooops: Something else went wrong")
                    finish()
                }
            }

        }
    }


}
