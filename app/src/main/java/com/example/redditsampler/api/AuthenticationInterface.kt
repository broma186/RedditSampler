package com.example.redditsampler.api

import com.example.redditsampler.data.AuthResponse
import com.example.redditsampler.data.Post
import com.example.redditsampler.viewmodels.AuthViewModel

interface AuthenticationInterface {
    fun retrievedAuthorization(authViewModel: AuthViewModel, authRes: List<AuthResponse>)
    fun retrievedAuthToken()
}