package com.example.livestream.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
data class Category(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,

    val image_path: String,
    val name: String,
)   {
    @Ignore
    constructor() : this(
        "",
        "",
        "",
    )
}