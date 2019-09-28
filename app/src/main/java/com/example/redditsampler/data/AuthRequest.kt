package com.example.redditsampler.data

data class AuthRequest (

    val grant_type : String,
    val code : String,
    val redirect_uri : String
)
