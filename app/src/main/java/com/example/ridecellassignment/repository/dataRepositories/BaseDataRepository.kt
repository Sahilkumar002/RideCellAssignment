package com.example.ridecellassignment.repository.dataRepositories

import android.util.Log
import com.example.ridecellassignment.repository.local.AppPreferences
import com.example.ridecellassignment.repository.retrofit.API
import com.example.ridecellassignment.repository.retrofit.RestClient
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.modals.RetrofitError
import okhttp3.ResponseBody
import retrofit2.Converter


abstract class BaseDataRepository(private val preferences: AppPreferences) {

    protected val restClient: API by lazy { RestClient.getClient() }

    protected fun handleRetrofitError(statusCode: Int, errorBody: ResponseBody?): PojoError? {
        Log.e("response status code", "$statusCode")
        var retrofitError: PojoError? = null

        val converter: Converter<ResponseBody, RetrofitError> =
            RestClient.getRetrofitInstance().responseBodyConverter(
                RetrofitError::class.java, arrayOfNulls(0)
            )
        var error: RetrofitError? = null
        errorBody?.let {
            error = converter.convert(errorBody)
        }

        error?.let {
            retrofitError = PojoError(it.error_description, false)
            if (it.error_code == 401) {
                preferences.clearPrefs()
            }
            (retrofitError as PojoError).errorCode = it.error_code
        }

        return retrofitError
    }
}