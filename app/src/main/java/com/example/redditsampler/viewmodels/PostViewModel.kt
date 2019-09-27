package com.example.redditsampler.viewmodels

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.Bindable
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
import retrofit2.http.Url
import java.net.URL
import kotlin.coroutines.CoroutineContext
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.redditsampler.R


class PostViewModel(post : Post): ViewModel() {

    val post : Post = checkNotNull(post)

    val title
        get() = post.data.title
    val link
        get() = post.data.permalink
    val thumbnail
        get() =  post.data.thumbnail



}

