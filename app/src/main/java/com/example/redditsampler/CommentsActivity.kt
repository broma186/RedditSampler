package com.example.redditsampler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentsResponse
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

    private var subreddit: String? = null
    private var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        subreddit = intent.getStringExtra(SUBREDDIT)
        id = intent.getStringExtra(ARTICLE_ID)
        Log.d("TEST", "subreddit2 : " + subreddit)
        Log.d("TEST", "id2 : " + id)


        getComments()
    }

    fun getComments() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<CommentsResponse> = RedditServiceHelper.getComments(subreddit, id)
            withContext(Dispatchers.Main) {
                try {
                    if (response != null) {
                        toast("CHEER BUDDAY!")
                        val res: Response<CommentsResponse> = response
                        Log.d("TEST", "succeees")
                    } else {
                        toast("OWHHA")
                        Log.d("TEST", "fail")
                    }
                } catch (e: HttpException) {
                    toast("\"Exception ${e.message}\"")
                } catch (e: Throwable) {
                    toast("Ooops: Something else went wrong")
                }
            }

        }
    }

}