package com.example.redditsampler

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.CommentResponse
import com.example.redditsampler.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.Response
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditsampler.adapters.CommentsAdapter
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.data.Comment
import com.example.redditsampler.databinding.ActivityCommentsBinding
import com.example.redditsampler.viewmodels.CommentsViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/*
Displays the comments for the selected post. Does not show replies, something that can be expanded
upon in future.
 */
class CommentsActivity : AppCompatActivity(), HasAndroidInjector {

    lateinit var binding: ActivityCommentsBinding
    lateinit var adapter : CommentsAdapter
    val context: Context = this

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    val commentsViewModel : CommentsViewModel? = CommentsViewModel(context, intent.getStringExtra(COMMENTS_LINK), object : CommentsInterface {
        override fun gotComments(comments: List<Comment>?, errorMessage: String?) {
            if (errorMessage.isNullOrBlank()) {
                setUpCommentsList(comments)
            } else {
                toast(errorMessage)
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView<ActivityCommentsBinding>(this, R.layout.activity_comments)
        setSupportActionBar(binding.toolbar)

        commentsViewModel?.getComments() // Get the comments using the permalink value
    }

    /*
    Initializes the adapter and adds it to the recycler view.
     */
    fun setUpCommentsList(comments: List<Comment>?) {
        binding.commentList.layoutManager = LinearLayoutManager(this)
        adapter = CommentsAdapter(comments)
        binding.commentList.adapter = adapter
    }
}