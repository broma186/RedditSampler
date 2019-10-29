package com.example.redditsampler.viewmodels

import android.content.Context
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.AuthenticationInterface
import com.example.redditsampler.api.PostsInterface
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response
import java.util.*

class PostsViewModel(val context: Context, val authInterface: PostsInterface) :
    ViewModel() {

    /* Downloads some reddit posts and calls the method that sets up the posts adapter for the list with
the posts.*/
    fun getPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            withContext(Dispatchers.Main) {
                try {
                    authInterface.gotPosts(response.body()?.data?.children, null)
                } catch (e: HttpException) {
                    authInterface.gotPosts(null, "\"Exception ${e.message}\"")
                } catch (e: Throwable) {
                    authInterface.gotPosts(null, "Ooops: Something else went wrong")
                }
            }
        }
    }
}