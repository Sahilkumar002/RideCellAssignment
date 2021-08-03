package com.example.ridecellassignment.modals

import androidx.annotation.Keep

@Keep
data class MultiSelectModel(var option: String, var isSelected: Boolean) {
    constructor(option: String) : this(option, false)

    override fun toString(): String {
        return option
    }
}

@Keep
data class VerifyEmailData(var email: String, var genPassword: String)

@Keep
data class ForgotPasswordData(var email: String, var genPassword: String)

@Keep
data class ChatActionModel(val actionId: Int, val actionName: String) {
    override fun toString(): String {
        return actionName
    }
}

@Keep
data class ChatModal(
    var type: Int, var time: String, var message: String? = "", var roomId: String? = "",
    var fileString: String? = ""
)

@Keep
data class ShareUserModal(val receiver_id: Int, val job_id: Int)


@Keep
data class ResourceStatus(val actionName: String, val actionId: Int) {
    override fun toString(): String {
        return actionName
    }
}





