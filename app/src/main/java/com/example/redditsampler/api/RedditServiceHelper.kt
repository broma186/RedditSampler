package com.example.redditsampler.api

import androidx.lifecycle.LiveData
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object RedditServiceHelper {


    suspend fun getPosts() : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getPosts()


}