package com.example.redditsampler.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.Post
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class PostViewModel(post : Post): ViewModel() {

    val post : Post = checkNotNull(post)

    val title
        get() = post.data.title
    val link
        get() = post.data.permalink
    val thumbnailUrl
        get() = post.data.thumbnail


}

