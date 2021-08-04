package com.example.ridecellassignment.repository.retrofit

import com.example.ridecellassignment.utils.AppGlobal
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", AppGlobal.AUTH_TOKEN)
            .header("Content-Type", "application/json")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}