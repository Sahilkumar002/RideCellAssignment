package com.example.ridecellassignment.repository.retrofit

import com.example.ridecellassignment.modals.PojoRegister
import com.example.ridecellassignment.modals.PojoUserData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


const val BASE_URL = "http://tutor.ideafoundation.in/public/api/"
private const val API_REGISTER = "register"
private const val API_LOGIN = "login"
private const val API_ACCEPT_INVITATIONS = "acceptStudentInvitation"
private const val API_REJECT_INVITATIONS = "rejectInvitation"
private const val API_UNBLOCK = "contactUs"



interface API {

    @FormUrlEncoded
    @POST(API_LOGIN)
    suspend fun apiLoginUser(
        @Field("email") userEmail: String, @Field("password") password: String,
        @Field("device_id") deviceId: String, @Field("device_token") deviceToken: String,
        @Field("device_type") deviceType: String
    ): Response<PojoUserData>

    @FormUrlEncoded
    @POST(API_REGISTER)
    suspend fun apiRegister(
        @Field("name") name: String, @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") userType: String, @Field("phone_number") phoneNumber: String,
        @Field("device_id") deviceId: String, @Field("device_token") deviceToken: String,
        @Field("device_type") deviceType: String, @Field("code") verifyCode: String
    ): Response<PojoRegister>



}