package com.example.redditsampler.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.AuthenticationInterface
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import retrofit2.Response
import java.util.*

class AuthViewModel(val context: Context, val authInterface: AuthenticationInterface, ) : ViewModel() {

    /*
Checks if there is an auth token response in database. If not, takes uer to the reddit permissions
screen in order to obtain a token. If there is a token and it isn't expired, downloads all posts, commencing
execution of the application. If the token has expired in reference to the expires_in attribute of the response,
then the user must tolerate the permissions screen again.
 */
    fun authenticateUserOrGetPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            val authRes: List<AuthResponse> = AuthApiHelper.getAuthorization(context)
            withContext(Dispatchers.Main) {
                authInterface.retrievedAuthorization(this, authRes)
            }
        }
    }

    /*
 Returns a boolean indicating that the reddit user authentication screen should or should not be displayed.
 Calculates if the currently stored authentication token has expired by comparing the current time with the
 storage time and then referring to the stored expiresIn time. The diff is calculated in milliseconds.
  */
    fun shouldShowAuthPermissionScreen(
        context: Context,
        authResponse: List<AuthResponse>
    ): Boolean {
        if (authResponse.size > 0) { // If there has been a previous authentication, see if auth is still valid
            val res = authResponse.get(0)
            val expiresInMillis = res.expires_in * 1000
            val lastLoginTime =
                context.getSharedPreferences(REDDIT_STORAGE, Context.MODE_PRIVATE).getLong(
                    AUTH_TOKEN_STORAGE_TIME, 0
                )
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

    fun generateState(): String {
        return UUID.randomUUID().toString()
    }

    fun parseAuthPage(state: String, url: String) : String? {

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
                return code
            } else {
                return null
            }
        } else if (url.contains(AUTH_FAILURE)) { // User has declined reddit permissions. Show page again
            return null
        }
        return null
    }

    /*
   Gets the OAuth token, stores the response in the database including the access_token field and
   the expires_in field, the only two that are used. The time of storage is also recorded to SP to
   check for expiry upon opening the app. Finally the auth token retrieval callback is called taking the
   user back to the posts activity to download the posts.
 */
    fun getAuthToken(context: Context, code: String) {
        val authorization: String =
            BASIC_AUTH_HEADER + Base64.encodeBase64String(BASIC_AUTH_STRING.toByteArray())
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<AuthResponse> = RedditServiceHelper.getRedditAuthToken(
                authorization, AUTH_REQUEST_CODE,
                code, AUTH_REDIRECT_URI
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    AuthApiHelper.writeAuthenticationToDb(response.body(), context)
                    storeAuthTimeStamp()
                    authInterface.retrievedAuthToken()
                }
            }
        }
    }

    fun storeAuthTimeStamp() {
        context.getSharedPreferences(REDDIT_STORAGE, Context.MODE_PRIVATE).edit().putLong(
            AUTH_TOKEN_STORAGE_TIME, System.currentTimeMillis()
        ).apply()
    }

    /* This url is opened in the post activity's webview. It is the reddit permissions screen that needs to be
* accepted so the user can use the app. It's only the comments that need an auth token to be displayed */
    fun getAuthUrl(state: String): String =
        "https://www.reddit.com/api/v1/authorize.compact?client_id=" + AUTH_CLIENT_ID + "&response_type=" + AUTH_RESPONSE_TYPE +
                "&state=" + state + "&redirect_uri=" + AUTH_REDIRECT_URI + "&duration=" + AUTH_DURATION + "&scope=" + AUTH_SCOPES


}