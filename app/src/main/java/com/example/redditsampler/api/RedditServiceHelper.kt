package com.example.redditsampler.api

import com.example.redditsampler.data.PostResponse
import retrofit2.Response

object RedditServiceHelper {


    suspend fun getPosts() : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getPosts()

    suspend fun getComments(subreddit : String?, article : String?) : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getComments(subreddit, article)


}