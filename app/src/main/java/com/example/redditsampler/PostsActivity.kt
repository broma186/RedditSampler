package com.example.redditsampler

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.Post
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.databinding.ActivityPostsBinding
import kotlinx.coroutines.*
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.AuthenticationInterface
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.utils.AuthUtils

/*
Displays either the reddit app use permissions screen in a webview, or the current posts.
 */
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

    /*
    Checks if there is an auth token response in database. If not, takes uer to the reddit permissions
    screen in order to obtain a token. If there is a token and it isn't expired, downloads all posts, commencing
    execution of the application. If the token has expired in reference to the expires_in attribute of the response,
    then the user must tolerate the permissions screen again.
     */
    fun authenticateUserOrGetPosts() {

        val authHelper = AuthUtils(this, object : AuthenticationInterface {
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

    fun setUpPostsList(posts: List<Post>?) {
        binding.postList.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(this, posts)
        binding.postList.adapter = adapter
    }

    /* Downloads some reddit posts and calls the method that sets up the posts adapter for the list with
     the posts.*/
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
}
