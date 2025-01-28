package com.example.hospitalapp.ui.receptionist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.SingleLiveEvent
import com.example.hospitalapp.models.Creation
import com.example.hospitalapp.models.ModelAllCalls
import com.vitatrack.hospitalsystem.models.ModelAllUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceptionistViewModel@Inject constructor(private val apiServices: ApiServices) :ViewModel( ) {


    private val _callsLiveData = MutableLiveData<ModelAllCalls?>()
    val callsLiveData get() = _callsLiveData


    fun getCalls(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiServices.getAllCalls(date)
            try {
                if (response.status == 1) {
                    _callsLiveData.postValue(response)
                } else {
                    _callsLiveData.postValue(null)
                }
            } catch (e: Exception) {
                _callsLiveData.postValue(null)
                }
            }

        }


    private val _createCallLiveData = MutableLiveData<Creation?>()
    val createCallLiveData get() = _createCallLiveData


    fun createCall(patientName: String, age: String, doctorId: Int, phone: String, description: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiServices.createCallReceptionist( patientName,  age,  doctorId,  phone,  description)
            try {
                if (response.status == 1){
                    _createCallLiveData.postValue(response)
                }else{
                    _createCallLiveData.postValue(null)
                }
            }catch (e:Exception){
                _createCallLiveData.postValue(null)
            }
        }
    }

    private val _selectDoctorLiveData = MutableLiveData<ModelAllUser?>()
    val selectDoctorLiveData get() = _selectDoctorLiveData

    fun selectDoctor( type: String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiServices.getEmployee(type, name)
            try {
                if (response.status == 1){
                   with(Dispatchers.Main){
                       _selectDoctorLiveData.postValue(response)
                   }
                }else{
                    with(Dispatchers.Main){
                        _selectDoctorLiveData.postValue(null)
                    }
                }
            }catch (e:Exception){
                with(Dispatchers.Main){
                    _selectDoctorLiveData.postValue(null)
                }
                }
        }
    }

    }
