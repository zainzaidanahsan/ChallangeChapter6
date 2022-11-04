package com.example.challangechapter6.view.vmView

import android.widget.Toast
import androidx.lifecycle.*
import com.example.challangechapter6.datastore.UserDataStoreManager
import com.example.challangechapter6.model.user.GetUserResponse
import com.example.challangechapter6.model.user.GetUserResponseItem
import com.example.challangechapter6.network.ApiServiceUser
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ApiServiceUser,
    private val pref: UserDataStoreManager
): ViewModel() {
    private val _user: MutableLiveData<GetUserResponseItem?> = MutableLiveData()
    val user: LiveData<GetUserResponseItem?> get() = _user


    fun auth(email: String, password: String) {
        client.getAllUsers()
            .enqueue(object : Callback<List<GetUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetUserResponseItem>>,
                    response: Response<List<GetUserResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            //Log.d(ContentValues.TAG, "onResponse: ${responseBody.toString()}")
                            for (i in responseBody.indices) {
                                if (responseBody[i].username.equals(
                                        email.toString(),
                                        ignoreCase = false
                                    ) && responseBody[i].password.equals(
                                        password.toString(), ignoreCase = false
                                    )
                                ) {
                                    _user.postValue(responseBody[i])
                                } else {
                                    _user.postValue(null)
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<List<GetUserResponseItem>>, t: Throwable) {
                }
            })
    }

    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }


    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }

    fun saveId(id: Int){
        viewModelScope.launch {
            pref.saveId(id)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }
}