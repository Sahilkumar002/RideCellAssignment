package com.example.ridecellassignment.repository.retrofit

import com.example.ridecellassignment.utils.AppGlobal
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
//            .header(
//                "Authorization", if (AppGlobal.AUTH_TOKEN.isNotBlank())
//                    String.format("%s %s", "Bearer", AppGlobal.AUTH_TOKEN) else AppGlobal.AUTH_TOKEN
//            )
      /*  Log.e(
            "Auth token", if (AppGlobal.AUTH_TOKEN.isNotBlank())
                String.format("%s %s", "Bearer", AppGlobal.AUTH_TOKEN) else "Empty AuthToken"
        )*/
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}