package com.example.ridecellassignment.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.ridecellassignment.repository.dataRepositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginRep: LoginRepository by lazy { LoginRepository(prefs) }

    fun userLogin(email: String?, password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
//            loginRep.userLogin(
//                email, password, deviceId, firebaseToken, deviceType
//            ) { response, error ->
//                isLoading.postValue(false)
//                if (response != null) {
//                    loginSuccess.postValue(true)
//                } else {
//                    errorLiveData.postValue(error)
//                }
//            }
            isLoading.postValue(false)
        }
    }


    @SuppressLint("HardwareIds")
    fun userSignUp(
        name: String?, email: String?, password: String?, confirmPassword: String?,
        userType: String, phoneNumber: String?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
//            loginRep.userRegister(
//                name, email, password, userType, phoneNumber, deviceId, firebaseToken,
//                deviceType, generatedPassword
//            ) { response, error ->
//                isLoading.postValue(false)
//                if (response != null) {
//                    registerSuccess.postValue(true)
//                } else {
//                    errorLiveData.postValue(error)
//                }
//            }
            isLoading.postValue(false)
        }
    }

}
