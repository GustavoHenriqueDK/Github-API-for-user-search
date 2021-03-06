package com.example.githubapiforusersearch.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    var name: String,
    @SerializedName("language")
    var language: String,
    @SerializedName("description")
    val description: String
) {
}