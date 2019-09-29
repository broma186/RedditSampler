package com.example.redditsampler.data

data class CommentResponse(
    val kind : String,
    val data : CommentInfo
)

data class CommentInfo(
    val modhash : String,
    val children: List<Comment>
)

data class Comment(
    val kind : String,
    val data : CommentAttributes
)

data class CommentAttributes(
    val author: String,
    val body: String
)
