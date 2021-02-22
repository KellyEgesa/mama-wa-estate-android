package com.mamawaestate.android.Network;

import com.mamawaestate.android.models.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BackEndApi {
    @POST("/api/auth/register")
    Call<UserData> registerUser(@Body UserData userData);
}
