package com.example.ridecellassignment.repository.dataRepositories

import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.repository.local.AppPreferences

class LoginRepository(private val preferences: AppPreferences) : BaseDataRepository(preferences) {

    suspend fun userLogin(
        userName: String, password: String, deviceId: String, deviceToken: String,
        deviceType: String, onResult: (loginResp: Boolean?, error: PojoError?) -> Unit
    ) {
        try {
            val response =
                restClient.apiLoginUser(userName, password, deviceId, deviceToken, deviceType)
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
        name: String, email: String, password: String, userType: String, phoneNumber: String,
        deviceId: String, deviceToken: String, deviceType: String, verifyCode: String,
        onResult: (loginResp: Boolean?, error: PojoError?) -> Unit
    ) {
        try {
            val response = restClient.apiRegister(
                name, email, password, userType, phoneNumber, deviceId, deviceToken, deviceType,
                verifyCode
            )
            if (response.isSuccessful && response.body() != null) {
                onResult(response.isSuccessful, null)
            } else {
                onResult(null, handleRetrofitError(response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            onResult(null, PojoError(e, false))
        }
    }


}