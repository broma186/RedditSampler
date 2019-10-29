package com.example.redditsampler

import com.example.redditsampler.data.Comment

interface CommentsInterface {
    fun gotComments(comments : List<Comment>?, errorMessage : String?)
}