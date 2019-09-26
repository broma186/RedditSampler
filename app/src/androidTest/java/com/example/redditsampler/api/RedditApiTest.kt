package com.example.redditsampler.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.redditsampler.api.RedditServiceHelper
import com.example.redditsampler.data.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RedditApiTest {

    @Test
    fun getPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<PostResponse> = RedditServiceHelper.getPosts()
            assertTrue(response.isSuccessful)
            withContext(Dispatchers.Main) {
                try {
                    // Do nothing
                } catch (e: HttpException) {
                    fail()
                } catch (e: Throwable) {
                    fail()
                }
            }
        }
    }
}
