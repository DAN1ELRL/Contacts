package com.example.daniel.innocv.Rest;

import com.example.daniel.innocv.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by daniel on 12/02/16.
 */
public interface ApiInterface {

    @GET("getall")
    Call<ApiResponse> getAllUsers();

    @GET("get/{id}")
    Call<ApiResponse> getUser(@Path("id") int id);

    @POST("create")
    Call<ApiResponse> createUser(@Body User user);

    @POST("update")
    Call<ApiResponse> updateUser(@Body User user);

    @GET("remove/{id}")
    Call<ApiResponse> deleteUser(@Path("id") String id);

    
}
