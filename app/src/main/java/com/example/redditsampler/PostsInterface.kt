package com.example.redditsampler

import com.example.redditsampler.data.Post

interface PostsInterface {
    fun gotPosts(posts : List<Post>?, errorMessage : String?)
}