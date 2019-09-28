package com.example.redditsampler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.data.PostResponse
import com.example.redditsampler.utils.ARTICLE_ID
import com.example.redditsampler.utils.COMMENTS_LINK
import com.example.redditsampler.utils.SUBREDDIT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response

class CommentsActivity : AppCompatActivity() {

    private var permalink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        permalink = intent.getStringExtra(COMMENTS_LINK)
        Log.d("TEST", "permalink : " + permalink)

        getComments()
    }

    fun getComments() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: ResponseBody = RedditServiceHelper.getComments(permalink)
            withContext(Dispatchers.Main) {
                try {
                    val res = response
                       // toast("CHEER BUDDAY!")
                        Log.d("TEST", "response getcomments: " + res.toString())
                } catch (e: HttpException) {
                  //  toast("\"Exception ${e.message}\"")
                } catch (e: Throwable) {
                   // toast("Ooops: Something else went wrong")
                }
            }

        }
    }

}