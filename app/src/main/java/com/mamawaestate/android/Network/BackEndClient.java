package com.mamawaestate.android.Network;

import com.mamawaestate.android.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackEndClient {
    public static Retrofit retrofit = null;

    public static BackEndApi urlRequest() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.MAMA_WA_ESTATE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(BackEndApi.class);
    }
}
