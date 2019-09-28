package com.example.redditsampler.utils

import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.util.*

object AuthHelper {

    fun getRedditAuthPermission(webView: WebView, context: AppCompatActivity) {

        val state : String = UUID.randomUUID().toString()
        webView.visibility = View.VISIBLE
        webView.settings.javaScriptEnabled = true
        try {
            webView.setWebViewClient(object : WebViewClient() {

                override fun onPageFinished(view: WebView, url: String) {

                    if (!url.contains(AUTH_FAILURE) && url.startsWith(AUTH_REDIRECT_URI)) {
                        val findState = url.substring(url.lastIndexOf(AUTH_STATE), url.indexOf(AUTH_RESPONSE_TYPE))
                        val returnedState = findState.substring(findState.indexOf("=") + 1)
                        if (state.equals(returnedState)) {
                            //TODO: DO post for state.

                        }
                    }
                    else if (url.contains(AUTH_FAILURE)) {
                        webView.loadUrl(AuthHelper.getAuthUrl(state))
                    }
                }
            })
            webView.loadUrl(AuthHelper.getAuthUrl(state))
        } catch (ex: Exception) {
            ex.printStackTrace()
            webView.visibility = View.GONE
        }
    }

    fun getAuthUrl(state : String) : String =
        "https://www.reddit.com/api/v1/authorize.compact?client_id=" + AUTH_CLIENT_ID + "&response_type=" + AUTH_RESPONSE_TYPE +
                "&state=" + state + "&redirect_uri=" + AUTH_REDIRECT_URI + "&duration=" + AUTH_DURATION + "&scope=" + AUTH_SCOPES


}