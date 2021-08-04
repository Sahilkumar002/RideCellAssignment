package com.example.ridecellassignment.repository.dataRepositories

import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.repository.local.AppPreferences

class HomeDataRepository(private val prefs: AppPreferences) : BaseDataRepository(prefs) {

    suspend fun logout(onResult: (result: Boolean?, message: String?, error: PojoError?) -> Unit) {
        try {
//            val response = restClient.apiLogout()
//            if (response.isSuccessful && null != response.body()) {
//                prefs.clearPrefs()
//                onResult(response.isSuccessful, response.body()?.message, null)
//            } else {
//                onResult(null, null, handleRetrofitError(response.code(), response.errorBody()))
//            }
        } catch (e: Exception) {
            onResult(null, null, PojoError(e, false))
        }

    }


    suspend fun updateProfile(
        userKey: String, onResult: (result: Boolean?, error: PojoError?) -> Unit
    ) {
        try {
            val response = restClient.apiGetUserProfile(userKey)
            if (response.isSuccessful && null != response.body()) {
                prefs.prefsUserProfile = response.body()
                onResult(response.isSuccessful, null)
            } else {
                onResult(null, handleRetrofitError(response.code(), response.errorBody()))
            }
        } catch (e: Exception) {
            onResult(null, PojoError(e, false))
        }

    }


}