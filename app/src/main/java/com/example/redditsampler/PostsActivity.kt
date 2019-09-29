package com.example.redditsampler

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.webkit.WebViewClient
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebView
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.AuthenticationInterface
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.utils.AUTH_TOKEN_STORAGE_TIME
import com.example.redditsampler.utils.AuthHelper
import com.example.redditsampler.utils.REDDIT_STORAGE


class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding
    lateinit var adapter: PostAdapter
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityPostsBinding>(this, R.layout.activity_posts)
        setSupportActionBar(binding.toolbar)

        authenticateUserOrGetPosts()
    }

    fun authenticateUserOrGetPosts() {

        val authHelper = AuthHelper(this, object : AuthenticationInterface {
            override fun retrievedAuthToken() {
                getPosts()
            }
        }, binding.authView)

        CoroutineScope(Dispatchers.IO).launch {
            val authRes: List<AuthResponse> = AuthApiHelper.getAuthorization(context)
            withContext(Dispatchers.Main) {
                if (authHelper.shouldShowAuthPermissionScreen(context, authRes)) {
                    authHelper.getRedditAuthPermission()
                } else {
                    getPosts()
                }
            }
        }
    }

    fun getPosts() {
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

    fun setUpPostsList(posts: List<Post>?) {
        binding.postList.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(this, posts)
        binding.postList.adapter = adapter
    }
}
