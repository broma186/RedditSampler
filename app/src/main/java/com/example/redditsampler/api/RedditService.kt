package com.example.redditsampler.api

import com.example.redditsampler.data.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {

    @GET(".json")
    suspend fun getPosts() : Response<PostResponse>

    @GET("r/{subreddit}/comments/{article}")
    suspend fun getComments(@Path("subreddit") subreddit : String?, @Path("article") article : String?) : Response<PostResponse>
}