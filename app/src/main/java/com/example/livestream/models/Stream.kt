package com.example.livestream.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Stream(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("status")
    val status: String? = null,

    val hls_url: String? = null,
    val vod_recording_hls_url: String? = null,
    val vod_duration: String? = null,

    @Embedded(prefix = "user_")
    val user: User? = User(),

    @Embedded(prefix = "category_")
    val category: Category? = Category()
) {
    @Ignore
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        User(),
        Category()
    )
}