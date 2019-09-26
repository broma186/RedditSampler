package com.example.redditsampler.viewmodels

import android.widget.ImageView
import androidx.databinding.BindingAdapter

data class PostViewModel (

    val thumbnailUrl : String,
    val title : String,
    val link : String



){

    @BindingAdapter("app:imageFromUrl")
    fun setImage(imageView : ImageView, url:String) {

    }
}

