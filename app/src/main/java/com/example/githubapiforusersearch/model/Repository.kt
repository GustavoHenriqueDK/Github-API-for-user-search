package com.example.githubapiforusersearch.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    var language: String
) {
}