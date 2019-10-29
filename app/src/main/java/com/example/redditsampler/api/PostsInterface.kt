package com.example.redditsampler.api

import com.example.redditsampler.data.Post

interface PostsInterface {
    fun gotPosts(posts : List<Post>?, errorMessage : String?)
}