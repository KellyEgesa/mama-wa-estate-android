package com.mamawaestate.android.Network;

import com.mamawaestate.android.models.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BackEndApi {
    @POST("/api/auth/register")
    Call<UserData> registerVendor(@Body UserData userData);
    @POST("/api/auth/login")
    Call<UserData> loginVendor(@Body UserData userData);
    @POST("/api/v1/register/")
    Call<UserData> registerConsumer(@Body UserData userData);
    @POST("/api/v1/login/")
    Call<UserData> loginUser(@Body UserData userData);
}
