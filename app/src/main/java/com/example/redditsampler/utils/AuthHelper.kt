package com.example.redditsampler.utils

import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.AuthRequest
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.HttpException
import retrofit2.Response
import java.net.URLEncoder
import java.util.*
import android.R.attr.data
import android.content.Context
import com.example.redditsampler.PostsActivity
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.AuthenticationInterface
import org.apache.commons.codec.binary.Base64
import kotlin.math.absoluteValue


class AuthHelper(val mContext : AppCompatActivity,
                 val authenticationInterface: AuthenticationInterface,
                 val webView: WebView) {


    fun shouldShowAuthPermissionScreen(context: Context, authResponse: List<AuthResponse>) : Boolean {
        if (authResponse.size > 0) { // If there has been a previous authentication, see if auth is still valid
            val res = authResponse.get(0)
            val expiresInMillis = res.expires_in * 1000
            val lastLoginTime = context.getSharedPreferences(REDDIT_STORAGE, Context.MODE_PRIVATE).getLong(
                AUTH_TOKEN_STORAGE_TIME, 0)
            val timeMillisBetweenNowAndLastLogin =
                System.currentTimeMillis() - lastLoginTime
            if (timeMillisBetweenNowAndLastLogin > expiresInMillis) { // Auth token has expired, get another 'temporary' one.
                return true
            } else {  // User auth token is still valid, use that.
                return false
            }
        } else { // User has never used the app, get auth credentials for first time.
            return true
        }
    }


    fun getRedditAuthPermission() {

        webView.visibility = View.VISIBLE
        val state: String = UUID.randomUUID().toString()
        try {
            webView.setWebViewClient(object : WebViewClient() {

                override fun onPageFinished(view: WebView, url: String) {
                    if (!url.contains(AUTH_FAILURE) && url.startsWith(AUTH_REDIRECT_URI)) {
                        val findState = url.substring(
                            url.lastIndexOf(AUTH_STATE),
                            url.indexOf(AUTH_RESPONSE_TYPE)
                        )
                        val returnedState =
                            findState.substring(findState.indexOf("=") + 1).replace("&", "")

                        if (state.equals(returnedState)) {
                            val codeWithCode = url.substring(url.lastIndexOf(AUTH_RESPONSE_TYPE))

                            val code = codeWithCode.substring(codeWithCode.indexOf("=") + 1)
                            getAuthToken(code)
                        }
                    } else if (url.contains(AUTH_FAILURE)) { // User has declined reddit permissions. Show page again
                        webView.loadUrl(getAuthUrl(state))
                    }
                }
            })
            webView.loadUrl(getAuthUrl(state))
        } catch (ex: Exception) {
            ex.printStackTrace()
            hideAuthView()
        }
    }

    private fun getAuthUrl(state: String): String =
        "https://www.reddit.com/api/v1/authorize.compact?client_id=" + AUTH_CLIENT_ID + "&response_type=" + AUTH_RESPONSE_TYPE +
                "&state=" + state + "&redirect_uri=" + AUTH_REDIRECT_URI + "&duration=" + AUTH_DURATION + "&scope=" + AUTH_SCOPES


    fun getAuthToken(code: String) {
        val authorization: String =
            BASIC_AUTH_HEADER + Base64.encodeBase64String(BASIC_AUTH_STRING.toByteArray())
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<AuthResponse> = RedditServiceHelper.getRedditAuthToken(
                authorization, AUTH_REQUEST_CODE,
                code, AUTH_REDIRECT_URI
            )
            withContext(Dispatchers.Main) {
                hideAuthView()
                val res  = response.body()
                if (response.isSuccessful) {
                    AuthApiHelper.writeAuthenticationToDb(response.body(), mContext)
                    storeAuthTimeStamp()
                    authenticationInterface.retrievedAuthToken()
                }
            }

        }
    }

    fun hideAuthView() {
        webView.visibility = View.GONE
    }

    fun storeAuthTimeStamp() {
        mContext.getSharedPreferences(REDDIT_STORAGE, Context.MODE_PRIVATE).edit().putLong(
            AUTH_TOKEN_STORAGE_TIME, System.currentTimeMillis()).apply()
    }



}