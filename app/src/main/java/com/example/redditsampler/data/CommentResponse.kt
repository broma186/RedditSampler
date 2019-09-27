package com.example.redditsampler.data

data class CommentResponse (

    val data : CommentInfo
)

data class CommentInfo (

    val children : List<Comment>
)


data class Comment (

    val data : PostAttributes
)

data class CommentAttributes (
    val subreddit : String
)
