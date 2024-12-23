package com.example.hospitalapp.ui.hr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.models.Data
import com.vitatrack.hospitalsystem.models.ModelAllUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(private val apiServices: ApiServices) :ViewModel() {


    private val _employeeLiveData = MutableLiveData<ModelAllUser?>()
    val employeeLiveData get() = _employeeLiveData

     fun getEmployee( type : String, name : String){
        viewModelScope.launch(IO) {
            val response = apiServices.getEmployee(type, name)
            try {
                if (response.status == 1 ){
                    _employeeLiveData.postValue(response)
                }else{
                    _employeeLiveData.postValue(null)
                }
            }catch (e : Exception){
                _employeeLiveData.postValue(null)

        }

        }
    }


}