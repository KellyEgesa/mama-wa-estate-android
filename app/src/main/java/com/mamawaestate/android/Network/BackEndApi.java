package com.mamawaestate.android.Network;

import com.mamawaestate.android.models.Products;
import com.mamawaestate.android.models.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BackEndApi<slug, category, image> {
    @POST("/api/auth/register")
    Call<UserData> registerVendor(@Body UserData userData);
    @POST("/api/auth/login")
    Call<UserData> loginVendor(@Body UserData userData);
    @POST("/api/v1/register/")
    Call<UserData> registerConsumer(@Body UserData userData);
    @POST("/api/v1/login/")
    Call<UserData> loginUser(@Body UserData userData);
//    @GET("/api/v1/products")
//    call("everything") products(@Body Products products)
//    @GET("everything")
//    Call<Products> getProducts(
//            @Query("tittle") String tittle
//             @Query("Slug") String slug
//            @Query("Image") String image
//                     @Query("Category") String category
//                     @Query("markedPrice") int markedPrice
//            @Query("sellingPrice") int sellingPrice
//            @Query("Description") String description
//            @Query("Warranty") String warranty
//            @Query("returnPolicy") String returnPolicy
//    );


}
