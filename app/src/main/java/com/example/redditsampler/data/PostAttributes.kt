package com.example.redditsampler.data

import retrofit2.http.Url

data class PostAttributes(

    val title: String,
    val thumbnail : String,
    val permalink : String,
    val subreddit : String,
    val id : String

)