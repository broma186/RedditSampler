package com.example.redditsampler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.PostsApiHelper
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response

class LaunchReddit : AppCompatActivity() {

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_reddit)

        storePosts()
    }


    fun storePosts() {
        CoroutineScope(Dispatchers.IO).launch{
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            withContext(Dispatchers.Main) {
                try {
                    PostsApiHelper.writeProductsToDb(response.body()?.data?.children, context)
                    goToPosts()
                } catch (e: HttpException) {
                    toast("\"Exception ${e.message}\"")
                    finish()
                } catch (e: Throwable) {
                    toast("Ooops: Something else went wrong")
                    finish()
                }
            }

        }
    }

    fun goToPosts() {
        val intent = Intent(applicationContext, PostsActivity::class.java)
        startActivity(intent)
        finish()
    }
}