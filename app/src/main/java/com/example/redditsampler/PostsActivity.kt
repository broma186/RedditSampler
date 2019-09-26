package com.example.redditsampler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.api.RedditService
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.databinding.ActivityPostsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response

class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityPostsBinding>(this,R.layout.activity_posts)
        setSupportActionBar(binding.toolbar)

        setUpPostsList()
        getPosts()
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


    fun getPosts() {
            CoroutineScope(Dispatchers.IO).launch {
                val response: Response<PostResponse> = RedditServiceHelper.getPosts()

                if (response.isSuccessful) {
                    Log.d("TEST", "SUCCESS")
                } else {
                    Log.d("TEST", "FAILURE")
                }
                withContext(Dispatchers.Main) {
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
