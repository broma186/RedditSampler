package com.example.redditsampler.api

import androidx.lifecycle.LiveData
import com.example.redditsampler.data.CommentsResponse
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {

    @GET(".json")
    suspend fun getPosts() : Response<PostResponse>

    @GET("r/{subreddit}/comments/{article}")
    suspend fun getComments(@Path("subreddit") subreddit : String?, @Path("article") article : String?) : Response<CommentsResponse>
}