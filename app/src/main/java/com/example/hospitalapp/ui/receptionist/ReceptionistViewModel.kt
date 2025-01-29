package com.example.hospitalapp.ui.receptionist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp.ApiServices
import com.example.hospitalapp.NetworkState
import com.example.hospitalapp.SingleLiveEvent
import com.example.hospitalapp.models.Creation
import com.example.hospitalapp.models.ModelAllCalls
import com.example.hospitalapp.models.ShowCall
import com.vitatrack.hospitalsystem.models.ModelAllUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


    private val _showCallLiveData = MutableLiveData<ShowCall?>()
    val showCallLiveData get() = _showCallLiveData

    fun showCall(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiServices.showCall(id)
            try {
                if (response.status == 1){
                  withContext(Dispatchers.Main){
                      _showCallLiveData.postValue(response)
                  }
                }else{
                    _showCallLiveData.postValue(null)
                }
            }catch (e:Exception){
                _showCallLiveData.postValue(null)
            }
        }
    }

    private val _logoutCallLiveData = MutableLiveData<NetworkState?>()
    val logoutCallLiveData get() = _logoutCallLiveData



    fun logoutCall(id : Int) {
        _logoutCallLiveData.postValue(NetworkState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = apiServices.logoutCall(id)
                if (data.status == 1) {
                   withContext(Dispatchers.Main){
                       _logoutCallLiveData.postValue(NetworkState.getLoaded(data))
                   }
                } else {
                    _logoutCallLiveData.postValue(NetworkState.getErrorMessage(data.message))

                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _logoutCallLiveData.postValue(NetworkState.getErrorMessage(ex))
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
                   withContext(Dispatchers.Main){
                       _selectDoctorLiveData.postValue(response)
                   }
                }else{
                        _selectDoctorLiveData.postValue(null)
                }
            }catch (e:Exception){
                    _selectDoctorLiveData.postValue(null)
                }
        }
    }

    }
