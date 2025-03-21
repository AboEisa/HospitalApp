package com.example.hospitalapp.ui.hr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.SingleLiveEvent
import com.example.hospitalapp.models.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class NewUserViewModel @Inject constructor(private val apiServices: ApiServices) : ViewModel() {


    private val _registerLiveData = MutableLiveData<Data?>()
    val registerLiveData: LiveData<Data?> get() = _registerLiveData


     fun register(
        email: String,
        password: String,
        fName: String,
        lName: String,
        gender: String,
        specialist: String,
        birthday: String,
        status: String,
        address: String,
        mobile: String,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiServices.register(
                email,
                password,
                fName,
                lName,
                gender,
                specialist,
                birthday,
                status,
                address,
                mobile,
                type
            )
           try {
               if (response.status == 1 ){
                  withContext(Dispatchers.Main){
                      _registerLiveData.postValue(response.data)
                  }
               }
           }catch (e:Exception){
             withContext(Dispatchers.Main){
                 _registerLiveData.postValue(null)
             }
        }

        }
    }
}



