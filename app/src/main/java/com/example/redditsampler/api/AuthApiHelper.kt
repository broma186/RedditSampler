package com.example.redditsampler.api

import android.content.Context
import android.view.View
import com.example.redditsampler.data.AppDatabase
import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.utils.AuthHelper
import retrofit2.Response

object AuthApiHelper {

     fun getAuthorization(context: Context) : List<AuthResponse> =
        AppDatabase.getInstance(context).authDao().getAuthorization()

    suspend fun writeAuthenticationToDb(authentication: AuthResponse?, context: Context) =
        AppDatabase.getInstance(context).authDao().insertAuth(authentication)

    suspend fun getAuthToken(context: Context) =
        AppDatabase.getInstance(context).authDao().getAuthToken()

}