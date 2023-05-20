package com.example.livestream.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,

    val email: String,
    val name: String,
    val password: String,
    val profilePicture: String,
    val followersNr: Int,
    var loggedIn: Boolean
)  {
    @Ignore
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0,
        false
    )
}