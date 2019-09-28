package com.example.redditsampler.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.utils.*
import retrofit2.Response
import java.util.*

object RedditServiceHelper {

    suspend fun getPosts(): Response<PostResponse> =
        RedditServiceFactory.createRedditService().getPosts()

    suspend fun getComments(subreddit: String?, article: String?): Response<PostResponse> =
        RedditServiceFactory.createRedditService().getComments(subreddit, article)





}