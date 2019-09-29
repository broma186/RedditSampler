package com.example.redditsampler.api

import android.content.Context
import com.example.redditsampler.data.AppDatabase
import com.example.redditsampler.data.AuthResponse

object AuthApiHelper {

     fun getAuthorization(context: Context) : List<AuthResponse> =
        AppDatabase.getInstance(context).authDao().getAuthorization()

    suspend fun writeAuthenticationToDb(authentication: AuthResponse?, context: Context) =
        AppDatabase.getInstance(context).authDao().insertAuth(authentication)

    suspend fun getAuthToken(context: Context) =
        AppDatabase.getInstance(context).authDao().getAuthToken()

}