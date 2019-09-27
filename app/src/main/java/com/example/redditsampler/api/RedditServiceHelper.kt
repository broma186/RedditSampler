package com.example.redditsampler.api

import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
import retrofit2.Response

object RedditServiceHelper {


    suspend fun getPosts() : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getPosts()

    suspend fun getComments(permalink : String?) : Response<CommentResponse> = RedditServiceFactory.
        createRedditService().getComments(permalink)


}