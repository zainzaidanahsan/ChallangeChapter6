package com.example.challangechapter6.view.vmView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challangechapter6.model.movie.GetDetailMovieResponse
import com.example.challangechapter6.network.ApiServiceMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieClient: ApiServiceMovie):ViewModel() {
    private val _movie: MutableLiveData<GetDetailMovieResponse?> = MutableLiveData()
    val movie: LiveData<GetDetailMovieResponse?> get() = _movie

    fun getMovieById(id: Int) {
        movieClient.getDetailMovie(id)
            .enqueue(object : Callback<GetDetailMovieResponse> {
                override fun onResponse(
                    call: Call<GetDetailMovieResponse>,
                    response: Response<GetDetailMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _movie.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetDetailMovieResponse>, t: Throwable) {

                }

            })
    }
}