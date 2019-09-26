package com.example.redditsampler.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter

data class Post (

    val thumbnailUrl : String,
    val title : String,
    val link : String



){

    @BindingAdapter("app:imageFromUrl")
    fun setImage(imageView : ImageView, url:String) {

    }
}

