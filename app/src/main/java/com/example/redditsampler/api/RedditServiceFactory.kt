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
    private var mGsonConverter: GsonConverterFactory? = null

    fun createRedditService(): RedditService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(mainClient)
            .build().create(RedditService::class.java)
    }

    val mainClient : OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                  val interceptor = HttpLoggingInterceptor()
                 interceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
                return httpBuilder.build()

            }
            return mClient!!
        }

    val gsonConverter: GsonConverterFactory
        get() {
            if(mGsonConverter == null){
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create())
            }
            return mGsonConverter!!
        }
}