package com.example.redditsampler.data

data class CommentResponse(

    val article : String,
    val comment : String?,
    val context : Int,
    val depth : Int?,
    val limit : Int?,
    val showedits : Boolean,
    val showmore : Boolean,
    val threaded : Boolean,
    val truncate : Int
)

data class CommentInfo(

    val permalink: String
)


data class Comment(

    val data: CommentAttributes
)

data class CommentAttributes(
    val body : NestedComment
)

data class NestedComment(
    val body : List<String>
)