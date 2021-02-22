package com.mamawaestate.android.api;

import com.mamawaestate.android.User;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/register")
    public void register(@Query("email") String email, Callback<User> callback);

}
