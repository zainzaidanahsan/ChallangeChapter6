package com.example.challangechapter6.view.vmView

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.challangechapter6.datastore.UserDataStoreManager
import com.example.challangechapter6.model.user.*
import com.example.challangechapter6.network.ApiServiceUser
import com.example.challangechapter6.worker.BlurWorker
import com.example.challangechapter6.worker.KEY_IMAGE_URI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileviewModel @Inject constructor(
    private val pref: UserDataStoreManager,
    private val client: ApiServiceUser,
    application: Application
): ViewModel() {
    private val _user: MutableLiveData<GetUserResponseItem?> = MutableLiveData()
    val user: LiveData<GetUserResponseItem?> get() = _user

    private val workManager = WorkManager.getInstance(application)

    private var imageUri: Uri? = null

    fun setImageUri(uri: Uri?) {
        imageUri = uri
    }
    // WorkRequest & beritahu WM untuk jalankan
    internal fun applyBlur() {
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri())
            .build()
        workManager.enqueue(blurRequest)
    }

    //create URI img
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }
    fun getUserById(id: Int) {
        client.getUserById(id)
            .enqueue(object : Callback<GetUserResponseItem> {
                override fun onResponse(
                    call: Call<GetUserResponseItem>,
                    response: Response<GetUserResponseItem>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _user.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetUserResponseItem>, t: Throwable) {
                }
            })
    }

    fun updateUser(
        id: Int,
        username: String,
        namaLengkap: String,
        tanggalLahir: String,
        alamat: String
    ) {
        client.updateUser(id, DataUserProfile(username, namaLengkap, tanggalLahir, alamat))
            .enqueue(object : Callback<PostUserResponse> {
                override fun onResponse(
                    call: Call<PostUserResponse>,
                    response: Response<PostUserResponse>
                ) {
                }

                override fun onFailure(call: Call<PostUserResponse>, t: Throwable) {
                }
            })
    }
    fun removeImage() {
        GlobalScope.launch {
            pref.removeImage()
        }
    }


    fun removeIsLoginStatus() {
        GlobalScope.launch {
            pref.removeIsLoginStatus()
        }
    }

    fun removeUsername() {
        GlobalScope.launch {
            pref.removeUsername()
        }
    }

    fun removeId() {
        GlobalScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun getId(): LiveData<Int> {
        return pref.getId.asLiveData()
    }

    fun saveUsername(username: String) {
        GlobalScope.launch {
            pref.saveUsername(username)
        }
    }

    fun saveImage(uri: String) {
        GlobalScope.launch {
            pref.saveProfileImage(uri)
        }
    }

}