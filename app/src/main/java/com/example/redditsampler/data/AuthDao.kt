package com.example.redditsampler.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthDao {

    @Query("SELECT * FROM auth_response")
    fun getAuthorization(): List<AuthResponse>

    @Query("SELECT access_token FROM auth_response")
    fun getAuthToken(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth(authResponse: AuthResponse?)
}