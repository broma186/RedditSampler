package com.example.redditsampler.data

data class AuthResponse (

    val access_token : String,
    val token_type : String,
    val expires_in : Int,
    val scope : String,
    val refresh_token : String
)