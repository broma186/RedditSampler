package com.example.redditsampler.data

data class CommentsResponse (

    val article : String,
    val comment : String,
    val context : Int,
    val showEdits : Boolean,
    val showmore : Boolean,
    val sort : String?,
    val threaded : Boolean,
    val truncate : Int

)