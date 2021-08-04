package com.example.ridecellassignment.modals

import androidx.annotation.Keep

@Keep
data class RetrofitError(val message: String, val error_code: Int)

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
data class PojoUserData(
    val display_name: String, var email: String, val key: String, var role: PojoPersonRole,
    val created_at: String, var updated_at: String
)


@Keep
data class PojoLoginResponse(val authentication_token: String, var person: PojoPersonData)

data class PojoPersonData(val display_name: String, val key: String, val role: PojoPersonRole)

data class PojoPersonRole(val key: String, val rank: Int)




