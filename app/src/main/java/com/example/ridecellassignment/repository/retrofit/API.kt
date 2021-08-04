package com.example.ridecellassignment.repository.retrofit

import com.example.ridecellassignment.modals.*
import retrofit2.Response
import retrofit2.http.*


const val BASE_URL = "https://blooming-stream-45371.herokuapp.com/"
private const val API_REGISTER = "api/v2/people/create"
private const val API_LOGIN = "api/v2/people/authenticate"
private const val API_GET_PROFILE = "api/v2/people/{person_key}"
private const val API_ACCEPT_INVITATIONS = "acceptStudentInvitation"
private const val API_REJECT_INVITATIONS = "rejectInvitation"
private const val API_UNBLOCK = "contactUs"


interface API {

    @POST(API_LOGIN)
    suspend fun apiLoginUser(@Body loginBody: LoginBody): Response<PojoLoginResponse>

    @POST(API_REGISTER)
    suspend fun apiRegister(@Body registerBody: RegisterBody): Response<PojoLoginResponse>

    @GET(API_GET_PROFILE)
    suspend fun apiGetUserProfile(@Path(value = "person_key") person_key: String): Response<PojoUserData>


}