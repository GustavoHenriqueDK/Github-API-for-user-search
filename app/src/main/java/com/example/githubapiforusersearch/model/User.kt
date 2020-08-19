package com.example.githubapiforusersearch.model

data class User(
    val searchedName: String,
    val name: String,
    val followers: String,
    val following: String,
    val email: String
) {}