package com.example.ridecellassignment.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.modals.RegisterBody
import com.example.ridecellassignment.repository.dataRepositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val loginRep: LoginRepository by lazy { LoginRepository(prefs) }

    val successLogin: LiveData<Boolean>
        get() = _successLogin
    private val _successLogin: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val successRegister: LiveData<Boolean>
        get() = _successRegister
    private val _successRegister: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    val errorLogin: LiveData<PojoError>
        get() = _errorLogin
    private val _errorLogin: MutableLiveData<PojoError> by lazy { MutableLiveData() }

    val errorRegister: LiveData<PojoError>
        get() = _errorRegister
    private val _errorRegister: MutableLiveData<PojoError> by lazy { MutableLiveData() }

    fun isAlreadyLogin() = prefs.isAlreadyLogin


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


    fun userSignUp(registerBody: RegisterBody) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            loginRep.userRegister(registerBody) { response, error ->
                isLoading.postValue(false)
                if (response != null) {
                    _successRegister.postValue(true)
                } else {
                    _errorRegister.postValue(error)
                }
            }
            isLoading.postValue(false)
        }
    }

}
