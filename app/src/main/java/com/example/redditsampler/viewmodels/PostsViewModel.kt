package com.example.redditsampler.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.redditsampler.PostsInterface
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class PostsViewModel(val context: Context, val postInterface: PostsInterface) :
    ViewModel() {

    /* Downloads some reddit posts and calls the method that sets up the posts adapter for the list with
the posts.*/
    fun getPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            withContext(Dispatchers.Main) {
                try {
                    postInterface.gotPosts(response.body()?.data?.children, null)
                } catch (e: HttpException) {
                    postInterface.gotPosts(null, "\"Exception ${e.message}\"")
                } catch (e: Throwable) {
                    postInterface.gotPosts(null, "Ooops: Something else went wrong")
                }
            }
        }
    }
}