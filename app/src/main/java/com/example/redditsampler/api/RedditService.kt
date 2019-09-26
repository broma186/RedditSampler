package com.example.redditsampler.api

import androidx.lifecycle.LiveData
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface RedditService {

    @GET(".json")
    suspend fun getPosts() : Response<PostResponse>
}