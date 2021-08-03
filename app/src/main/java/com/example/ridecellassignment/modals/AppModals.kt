package com.example.ridecellassignment.modals

import androidx.annotation.Keep

@Keep
data class LoginBody(var email: String, var password: String)

@Keep
data class RegisterBody(var display_name: String, var email: String, var password: String)






