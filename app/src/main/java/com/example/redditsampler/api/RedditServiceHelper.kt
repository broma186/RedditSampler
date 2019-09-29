package com.example.redditsampler.api

import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import okhttp3.ResponseBody
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
import com.example.redditsampler.data.AuthRequest
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.utils.*
import retrofit2.Response
import java.util.*

object RedditServiceHelper {

    suspend fun getPosts(): Response<PostResponse> =
        RedditServiceFactory.createRedditService(BASE_URL).getPosts()

    suspend fun getComments(permalink : String?) : Response<CommentResponse> = RedditServiceFactory.
        createRedditService(BASE_URL_AUTH).getComments(permalink)

    suspend fun getRedditAuthToken(authorization : String, grantType: String, code : String, redirectUri : String) : Response<AuthResponse> = RedditServiceFactory.
        createRedditService(BASE_URL).getRedditAuthToken(authorization, grantType, code, redirectUri)
}