package com.example.githubapiforusersearch.rest;

import com.example.githubapiforusersearch.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EndPoint {

    @GET("users/{user}")
    Call<User> searchForUser(@Path("user") String user);

}
