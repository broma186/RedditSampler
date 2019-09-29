package com.example.redditsampler.utils

import android.content.Context
import com.example.redditsampler.api.AuthApiHelper
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.AuthResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response

class AuthUtilsTest {

    @Test
    fun shouldShowAuthPermissionScreen() {
        var auth = AuthResponse(0, "", "", 3600, "", "")
        var authResponse : ArrayList<AuthResponse> = arrayListOf()
        authResponse.add(auth)

        if (authResponse.size > 0) {
            val res = authResponse.get(0)
            val expiresInMillis = res.expires_in * 1000
            val lastLoginTime = System.currentTimeMillis() - 4600000
            val timeMillisBetweenNowAndLastLogin =
                System.currentTimeMillis() - lastLoginTime

            assertTrue(timeMillisBetweenNowAndLastLogin > expiresInMillis)
            assertFalse(authResponse.size == 0)
        } else { // User has never used the app, get auth credentials for first time.
           assertTrue(authResponse.size == 0)
        }
    }
}