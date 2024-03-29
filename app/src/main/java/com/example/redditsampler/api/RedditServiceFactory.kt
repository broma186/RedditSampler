package com.example.redditsampler.api

import com.example.redditsampler.utils.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object RedditServiceFactory {

    private var mClient: OkHttpClient? = null

    fun createRedditService(baseUrl : String): RedditService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(mainClient)
            .build().create(RedditService::class.java)
    }

    // Initially set up for debugging with the logging interceptor.
    val mainClient : OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                  /*val interceptor = HttpLoggingInterceptor()
                 interceptor.level = HttpLoggingInterceptor.Level.BODY*/
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                   // .addInterceptor(interceptor)
                mClient = httpBuilder.build()

            }
            return mClient!!
        }
}