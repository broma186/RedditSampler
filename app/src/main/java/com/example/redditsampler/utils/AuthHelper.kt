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
import org.apache.commons.codec.binary.Base64



object AuthHelper {

    fun getRedditAuthPermission(webView: WebView, context: AppCompatActivity) {

        val state : String = UUID.randomUUID().toString()
        Log.d("TEST", "state is : " + state)

        webView.visibility = View.VISIBLE
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
                        Log.d("TEST", "returned state is : " + returnedState)

                        if (state.equals(returnedState)) {
                            val codeWithCode = url.substring(url.lastIndexOf(AUTH_RESPONSE_TYPE))
                            Log.d("TEST", "Codewithcode is : " + codeWithCode)

                            val code = codeWithCode.substring(codeWithCode.indexOf("=") + 1)
                            Log.d("TEST", "Code is : " + code)
                            getAuthToken(code)
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


fun getAuthToken(code : String)  {

    var authStr = AUTH_CLIENT_ID
    val authorization : String = Base64.encodeBase64String(authStr.toByteArray())
    CoroutineScope(Dispatchers.IO).launch {
            val response: Response<AuthResponse> = RedditServiceHelper.getRedditAuthToken(authorization, AUTH_REQUEST_CODE,
                code, AUTH_REDIRECT_URI)
            withContext(Dispatchers.Main) {
                val res = response
                if (response.isSuccessful) {
                    Log.d("TEST", "response is successful")
                } else {
                    Log.d("TEST", "response is Unsuccessful")
                }
                try {
                } catch (e: HttpException) {
                } catch (e: Throwable) {
                }
            }

        }
    }


}