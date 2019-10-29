package com.example.redditsampler

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditsampler.adapters.PostAdapter
import com.example.redditsampler.data.Post
import com.example.redditsampler.databinding.ActivityPostsBinding
import org.jetbrains.anko.toast
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.viewmodels.AuthViewModel
import com.example.redditsampler.viewmodels.PostsViewModel
import dagger.android.AndroidInjection

/*
Displays either the reddit app use permissions screen in a webview, or the current posts.
 */
class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPostsBinding
    lateinit var adapter: PostAdapter
    val context: Context = this

    val postsViewModel: PostsViewModel? = PostsViewModel(context, object :
        PostsInterface {
        override fun gotPosts(posts: List<Post>?, errorMessage: String?) {
            if (errorMessage.isNullOrBlank()) {
                setUpPostsList(posts)
            } else {
                toast(errorMessage)
            }
        }
    })

    val authViewModel: AuthViewModel? = AuthViewModel(context, object :
        AuthenticationInterface {
        override fun retrievedAuthorization(authViewModel: AuthViewModel, authRes: List<AuthResponse>) {
            if (authViewModel.shouldShowAuthPermissionScreen(context, authRes)) {
                showAuthView()
                setupAuthView()
            } else {
                postsViewModel?.getPosts()
            }
        }

        override fun retrievedAuthToken() {
            hideAuthView()
            postsViewModel?.getPosts()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView<ActivityPostsBinding>(this, R.layout.activity_posts)

        setSupportActionBar(binding.toolbar)

        authViewModel?.authenticateUserOrGetPosts()
    }

    fun showAuthView() {
        binding.authView.visibility = View.VISIBLE
    }

    /*
       Takes the webView from the posts screen initialized in the constructor and loads the reddit auth
       permission url using query fields stored in constants and redirects. State and code is parsed rather
       crudely using substrings.
    */
    fun setupAuthView() {
        val state = authViewModel?.generateState()
        try {
            binding.authView.setWebViewClient(object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val code = authViewModel?.parseAuthPage(state, url)
                    if (!code.isNullOrBlank()) {
                        authViewModel?.getAuthToken(context, code)
                    } else {
                        binding.authView.loadUrl(authViewModel?.getAuthUrl(state))
                    }
                }
            })
            binding.authView.loadUrl(authViewModel?.getAuthUrl(state))
        } catch (ex: Exception) {
            ex.printStackTrace()
            hideAuthView()
        }
    }

    fun hideAuthView() {
        binding.authView.visibility = View.GONE
    }

    fun setUpPostsList(posts: List<Post>?) {
        binding.postList.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(this, posts)
        binding.postList.adapter = adapter
    }
}
