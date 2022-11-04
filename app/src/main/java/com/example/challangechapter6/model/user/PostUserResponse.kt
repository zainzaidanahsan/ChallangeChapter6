package com.example.challangechapter6.model.user


import com.google.gson.annotations.SerializedName

data class PostUserResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)