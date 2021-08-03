package com.example.ridecellassignment.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.repository.dataRepositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginRep: LoginRepository by lazy { LoginRepository(prefs) }

    val successLogin: LiveData<Boolean>
        get() = _successLogin
    private val _successLogin: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val errorLogin: LiveData<PojoError>
        get() = _errorLogin
    private val _errorLogin: MutableLiveData<PojoError> by lazy { MutableLiveData() }


    fun userLogin(loginBody: LoginBody) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            loginRep.userLogin(loginBody) { response, error ->
                isLoading.postValue(false)
                if (response != null) {
                    _successLogin.postValue(true)
                } else {
                    _errorLogin.postValue(error)
                }
            }
            isLoading.postValue(false)
        }
    }


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
