package com.example.ridecellassignment.repository.dataRepositories

import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.modals.RegisterBody
import com.example.ridecellassignment.repository.local.AppPreferences

class LoginRepository(private val preferences: AppPreferences) : BaseDataRepository(preferences) {

    suspend fun userLogin(
        login: LoginBody, onResult: (loginResp: Boolean?, error: PojoError?) -> Unit
    ) {
        try {
            val response = restClient.apiLoginUser(login)
            if (response.isSuccessful && response.body() != null) {
                preferences.loginUser(response.body())
                onResult(response.isSuccessful, null)
            } else {
                onResult(null, handleRetrofitError(response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            onResult(null, PojoError(e, false))
        }
    }

    suspend fun userRegister(
        registerBody: RegisterBody, onResult: (loginResp: Boolean?, error: PojoError?) -> Unit
    ) {
        try {
            val response = restClient.apiRegister(registerBody)
            if (response.isSuccessful && response.body() != null) {
                preferences.loginUser(response.body())
                onResult(response.isSuccessful, null)
            } else {
                onResult(null, handleRetrofitError(response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            onResult(null, PojoError(e, false))
        }
    }


}