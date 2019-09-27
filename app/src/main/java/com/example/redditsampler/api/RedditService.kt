package com.example.redditsampler.api

import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

interface RedditService {

    @GET(".json")
    suspend fun getPosts() : Response<PostResponse>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @GET()
    suspend fun getComments(@Url url: String?) : Response<CommentResponse>
}