package com.example.redditsampler.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "auth_response",
    indices = [Index("id")]
)
data class AuthResponse (
    @PrimaryKey @ColumnInfo(name = "id") val id : Int = 1, // Keep the same so is replaced every auth download.
    @ColumnInfo(name = "access_token") val access_token : String,
    @ColumnInfo(name = "token_type") val token_type : String,
    @ColumnInfo(name = "expires_in") val expires_in : Int,
    @ColumnInfo(name = "scope") val scope : String,
    @ColumnInfo(name = "refresh_token") val refresh_token : String?
)