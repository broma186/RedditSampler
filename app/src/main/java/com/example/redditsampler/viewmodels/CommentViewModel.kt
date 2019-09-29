package com.example.redditsampler.viewmodels

import androidx.lifecycle.ViewModel
import com.example.redditsampler.data.Comment

class CommentViewModel(comment : Comment): ViewModel() {

    val comment : Comment = checkNotNull(comment)

    val author
        get() =  comment.data.author
    val body
        get() = comment.data.body

}