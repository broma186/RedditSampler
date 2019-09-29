package com.example.redditsampler

import android.content.Context
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.utils.AUTH_HEADER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import retrofit2.Response

class CommentsActivityTest {

    @Test
    fun getComments() {
        val context = Mockito.mock(Context::class.java)
        val permalink = "/r/technology/comments/daihmr/china_unveils_500_megapixel_camera_that_can/"
        CoroutineScope(Dispatchers.IO).launch {
            val authorization =  AUTH_HEADER + AuthApiHelper.getAuthToken(context)
            val response: Response<List<CommentResponse>> =
                RedditServiceHelper.getComments(authorization, permalink)
            withContext(Dispatchers.Main) {
                assertTrue(response.isSuccessful)
                assertTrue(response.body()?.get(1)?.data?.children?.size!! > 0)
            }
        }
    }
}