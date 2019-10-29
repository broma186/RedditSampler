package com.example.redditsampler.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.redditsampler.CommentsInterface
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.utils.AUTH_HEADER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.Response

class CommentsViewModel(val context : Context, val permalink: String?, val commentsInterface : CommentsInterface): ViewModel() {

    /*
   Downloads comments for the selected article in the posts activity.
    */
    fun getComments() {
        CoroutineScope(Dispatchers.IO).launch {
            val authorization =  AUTH_HEADER + AuthApiHelper.getAuthToken(context)
            val response: Response<List<CommentResponse>> =
                RedditServiceHelper.getComments(authorization, permalink)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    commentsInterface.gotComments(response.body()?.get(1)?.data?.children, null)
                } else {
                    commentsInterface.gotComments(null, "Failed to get comments")
                }
            }
        }
    }


}