package com.example.redditsampler.api

import android.content.Context
import com.example.redditsampler.data.Post
import com.example.redditsampler.data.AppDatabase

object  PostsApiHelper {

    suspend fun writeProductsToDb(products: List<Post>?, context: Context) =
        AppDatabase.getInstance(context).productDao().insertAll(products)

}