package com.example.redditsampler.api

import com.example.redditsampler.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RedditServiceFactory {

    fun createRedditService(): RedditService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RedditService::class.java)
    }
}