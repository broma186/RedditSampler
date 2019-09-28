package com.example.redditsampler.api

import com.example.redditsampler.data.AuthRequest
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RedditService {

    @GET(".json")
    suspend fun getPosts() : Response<PostResponse>

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json")
    @GET()
    suspend fun getComments(@Url url: String?) : ResponseBody

    @POST("api/v1/access_token")
    suspend fun getRedditAuthToken(@Body authRequest : AuthRequest) : Response<AuthResponse>
}