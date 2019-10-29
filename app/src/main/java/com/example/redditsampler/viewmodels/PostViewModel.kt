package com.example.redditsampler.viewmodels

import android.content.Context
import android.content.Intent
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
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.redditsampler.CommentsActivity
import com.example.redditsampler.R
import com.example.redditsampler.utils.BASE_URL
import com.example.redditsampler.utils.COMMENTS_LINK
import com.example.redditsampler.utils.LINK_BASE


class PostViewModel(val context : Context, post : Post): ViewModel() {

    val post : Post = checkNotNull(post)

    val id
        get() =  post.data.id
    val title
        get() = post.data.title
    val link
        get() = post.data.permalink
    val thumbnail
        get() =  post.data.thumbnail

    fun goToComments() {
        val intent = Intent(context, CommentsActivity::class.java)
        intent.putExtra(COMMENTS_LINK, link)
        ContextCompat.startActivity(context, intent, null)
    }

    // Opens the reddit post specified by the post object's link in a browser.
    fun openPost() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(LINK_BASE + link)
        ContextCompat.startActivity(context, i, null)
    }

}

