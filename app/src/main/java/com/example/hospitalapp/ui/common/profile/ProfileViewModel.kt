package com.example.hospitalapp.ui.common.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.SingleLiveEvent
import com.example.hospitalapp.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(private val apiServices: ApiServices) : ViewModel() {

    private val _profileLiveData = SingleLiveEvent<UserModel?>()
    val profileLiveData get() = _profileLiveData

    fun getProfile(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServices.showProfile(id)
                if (response.status == 1) {
                    _profileLiveData.postValue(response)
                } else {
                    _profileLiveData.postValue(null)
                }
            }catch (e: Exception){
                _profileLiveData.postValue(null)
            }

        }

    }
}