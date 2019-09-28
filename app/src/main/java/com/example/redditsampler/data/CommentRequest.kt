package com.example.redditsampler.data

data class CommentRequest (

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