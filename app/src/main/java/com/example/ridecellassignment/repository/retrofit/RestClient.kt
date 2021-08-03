package com.example.ridecellassignment.repository.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {

    private lateinit var retrofit: Retrofit

    private val httpClient: OkHttpClient = OkHttpClient.Builder().also { builder ->
        builder.addInterceptor(AuthInterceptor())
        builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        builder.connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90,TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
    }.build()


    fun getClient(): API {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()
        return retrofit.create(API::class.java)
    }

    fun getRetrofitInstance() = retrofit
}