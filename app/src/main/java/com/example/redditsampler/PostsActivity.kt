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

        viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        setUpPostsList()
         getPosts()
    }

    fun setUpPostsList() {
        adapter = PostAdapter()
        binding.postList.adapter = adapter
        //subscribeUi(adapter)
    }


    fun getPosts()  {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            if (response.isSuccessful) {
                toast("Got posts")
            } else {
                toast("Error: ${response.code()}")
            }
            withContext(Dispatchers.Main) {
                val postResponse : PostResponse? = response.body()
                val posts: List<Post>? = postResponse?.data?.children


                try {
                    // Do nothing
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
