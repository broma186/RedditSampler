package com.example.redditsampler.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.utils.*
import retrofit2.Response
import java.util.*

object RedditServiceHelper {

    suspend fun getPosts() : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getPosts()

    suspend fun getComments(subreddit : String?, article : String?) : Response<PostResponse> = RedditServiceFactory.
        createRedditService().getComments(subreddit, article)


    fun getRedditAuthPermission(context : AppCompatActivity) {
       val STATE : String = UUID.randomUUID().toString()
       val url : String = "https://www.reddit.com/api/v1/authorize.compact?client_id=" + AUTH_CIENT_ID + "&response_type=" + AUTH_RESPONSE_TYPE +
               "&state=" + STATE + "&redirect_uri=" + AUTH_REDIRECT_URI + "&duration=" + AUTH_DURATION + "&scope=" + AUTH_SCOPES
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivityForResult(context, i, BROWSER_RESULT, null)
    }




}