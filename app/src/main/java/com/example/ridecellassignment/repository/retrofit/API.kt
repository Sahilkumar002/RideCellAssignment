package com.example.ridecellassignment.repository.retrofit

import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.modals.PojoRegister
import com.example.ridecellassignment.modals.PojoUserData
import com.example.ridecellassignment.modals.RegisterBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val BASE_URL = "https://blooming-stream-45371.herokuapp.com/"
private const val API_REGISTER = "api/v2/people/create"
private const val API_LOGIN = "api/v2/people/authenticate"
private const val API_ACCEPT_INVITATIONS = "acceptStudentInvitation"
private const val API_REJECT_INVITATIONS = "rejectInvitation"
private const val API_UNBLOCK = "contactUs"


interface API {

    @FormUrlEncoded
    @POST(API_LOGIN)
    suspend fun apiLoginUser(@Body loginBody: LoginBody): Response<PojoUserData>

    @FormUrlEncoded
    @POST(API_REGISTER)
    suspend fun apiRegister(@Body registerBody: RegisterBody): Response<PojoRegister>


}