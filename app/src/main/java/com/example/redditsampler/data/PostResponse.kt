package com.example.redditsampler.data

data class PostResponse (
    val kind : String,
    val data : PostInfo
)

data class PostInfo (
    val children : List<Post>
)

data class Post(

    val kind : String,
    val data : PostAttributes

)

data class PostAttributes(

    val title: String,
    val thumbnail : String,
    val permalink : String,
    val subreddit : String,
    val id : String

)