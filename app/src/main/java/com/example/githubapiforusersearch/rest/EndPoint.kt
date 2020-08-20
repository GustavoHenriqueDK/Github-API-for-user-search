package com.example.githubapiforusersearch.rest

import com.example.githubapiforusersearch.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {
    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>
}