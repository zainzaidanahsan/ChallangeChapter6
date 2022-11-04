package com.example.challangechapter6.view.vmView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challangechapter6.datastore.UserDataStoreManager
import com.example.challangechapter6.model.movie.GetMoviePopularResponse
import com.example.challangechapter6.model.movie.ResultMovie
import com.example.challangechapter6.network.ApiServiceMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieClient: ApiServiceMovie,
    private val pref: UserDataStoreManager
) : ViewModel(){
    private val _movie: MutableLiveData<List<ResultMovie>> = MutableLiveData()
    val movie: LiveData<List<ResultMovie>> get() = _movie

    fun setMoviesList() {
        movieClient.getListMovie()
            .enqueue(object : Callback<GetMoviePopularResponse> {
                override fun onResponse(
                    call: Call<GetMoviePopularResponse>,
                    response: Response<GetMoviePopularResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            _movie.postValue(data.results as List<ResultMovie>?)
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviePopularResponse>, t: Throwable) {

                }

            })
    }


    fun getDataStoreUsername(): LiveData<String> {
        return pref.getUsername.asLiveData()
    }
}