package com.example.challangechapter6.network

import com.example.challangechapter6.model.user.*
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {
    @GET("users")
    fun getAllUsers(): Call<List<GetUserResponseItem>>

    @POST("users")
    fun insertUser(@Body request: DataUser): Call<PostUserResponse>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id : Int, @Body request: DataUserProfile): Call<PostUserResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<GetUserResponseItem>
}