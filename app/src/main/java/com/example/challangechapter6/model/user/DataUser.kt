package com.example.challangechapter6.model.user

import com.google.gson.annotations.SerializedName

class DataUser(
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("nama_lengkap")
    val namaLengkap: String?,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String?,
    @SerializedName("alamat")
    val alamat: String?
)