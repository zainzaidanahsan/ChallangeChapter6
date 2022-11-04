package com.example.challangechapter6.network

import com.example.challangechapter6.model.movie.GetDetailMovieResponse
import com.example.challangechapter6.model.movie.GetMoviePopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceMovie {
    @GET("/3/movie/popular?api_key=04efbf06cd8d57a478d9101141a9a1e8")
    fun getListMovie():Call<GetMoviePopularResponse>

    @GET("/3/movie/{id}?api_key=04efbf06cd8d57a478d9101141a9a1e8")
    fun getDetailMovie(@Path("id") id: Int) : Call<GetDetailMovieResponse>
}