package com.example.challangechapter6.model.user


import com.google.gson.annotations.SerializedName

data class GetUserResponseItem(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("nama_lengkap")
    val namaLengkap: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String,
    @SerializedName("username")
    val username: String
)