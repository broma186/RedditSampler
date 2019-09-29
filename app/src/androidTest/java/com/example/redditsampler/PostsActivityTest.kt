package com.example.redditsampler

import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class PostsActivityTest {

    @Test
    fun getPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            withContext(Dispatchers.Main) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertTrue(response.body()?.data != null)
            }
        }
    }
}