package com.mamawaestate.android.api;

import android.util.Log;

import com.mamawaestate.android.User;

import retrofit2.Callback;
import retrofit2.Retrofit;

public class mamwaestateclient<username, email> {
    Retrofit retrofit;

    Api api = retrofit.create(Api.class);
api.register(email, username, new Callback<User>()
    private Throwable error;

    private Object Response;

    private Object response;

    private Object RetrofitError;

    {
        @Override
        public void success(User Object user;
        user, Response response) {
            // login logic
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("Retrofit", error.getMessage());
        }
    }

    public mamwaestateclient(Object response, Object response1, Object retrofitError) {
        Response = response;
        this.response = response1;
        RetrofitError = retrofitError;
        error = new Throwable();
        retrofit = new Retrofit.Builder()
                .setEndpoint("https://mamawaestate.herokuapp.com/")
                .build();
    });

}
