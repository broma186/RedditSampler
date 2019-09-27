package com.example.redditsampler.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import retrofit2.http.Url

@Entity(tableName = "posts",
    indices = [Index("product_id")]
)
data class PostAttributes(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "post_id") val postId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "permalink") val permalink: String
)

