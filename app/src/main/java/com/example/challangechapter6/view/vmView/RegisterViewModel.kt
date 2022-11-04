package com.example.challangechapter6.view.vmView

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challangechapter6.model.user.DataUser
import com.example.challangechapter6.model.user.PostUserResponse
import com.example.challangechapter6.network.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val client : ApiServiceUser)
    : ViewModel() {
    private val _user: MutableLiveData<PostUserResponse?> = MutableLiveData()

    fun insertUser(username: String, email: String, password: String) {
        client.insertUser(
            DataUser(username, email, password, "", "", "")
        )
            .enqueue(object : retrofit2.Callback<PostUserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PostUserResponse>,
                    response: retrofit2.Response<PostUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    } else {
                        Log.d("Error", response.message())
                        _user.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<PostUserResponse>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                    _user.postValue(null)
                }

            })
    }
}