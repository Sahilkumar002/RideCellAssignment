package com.example.ridecellassignment.modals

import androidx.annotation.Keep

@Keep
data class RetrofitError(val error_description: String, val error_code: Int)

@Keep
data class PojoError(
    var errorString: String? = "", var exception: Exception? = null,
    var isRepeatable: Boolean? = false
) {

    var errorCode: Int? = null

    constructor(error: String, isRepeatable: Boolean? = false) : this(error, null, isRepeatable)

    constructor(exception: Exception?, isRepeatable: Boolean?) :
            this("", exception, isRepeatable)

}

@Keep
data class PojoCommon(val message: String)

@Keep
data class PojoRegister(
    val id: Int, val name: String, val email: String, var email_verified_at: String?,
    val phone_number: String, val user_type: String, val is_active: Int, val is_block: Int,
    val device_id: String, val device_token: String, val token: String
)



@Keep
data class PojoUserData(
    val id: Int, var name: String, val user_type: String, var profile_image: String?,
)