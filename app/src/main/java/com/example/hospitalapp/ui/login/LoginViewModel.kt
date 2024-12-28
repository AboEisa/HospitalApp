package com.example.hospitalapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.models.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers


@HiltViewModel
class LoginViewModel @Inject constructor(private val apiServices: ApiServices) : ViewModel() {

    private val _loginLiveData = MutableLiveData<UserModel?>()
    val loginLiveData  get() = _loginLiveData

    fun login(email: String, password: String, deviceToken: String ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServices.login(email, password, deviceToken)
              if (response.status == 1){
                  _loginLiveData.postValue(response)
              }
            } catch (e: Exception) {
                _loginLiveData.postValue(null)
            }
        }
    }
}
