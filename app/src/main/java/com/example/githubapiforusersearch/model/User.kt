package com.example.githubapiforusersearch.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("followers")
    val followers: String,
    @SerializedName("following")
    val following: String,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("email")
    val email: String
) {}