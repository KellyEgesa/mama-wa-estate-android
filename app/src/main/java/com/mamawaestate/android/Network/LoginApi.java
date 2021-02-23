package com.mamawaestate.android.Network;

import com.mamawaestate.android.com.moringaschool.mamawaestate.Data;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {
    @POST("login")
    Call<Data> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

}
