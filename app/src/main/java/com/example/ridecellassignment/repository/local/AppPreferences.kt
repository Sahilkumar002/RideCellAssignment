package com.example.ridecellassignment.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.example.ridecellassignment.modals.PojoLoginResponse
import com.example.ridecellassignment.modals.PojoPersonData
import com.google.gson.Gson
import com.example.ridecellassignment.utils.AppGlobal

private const val PREFS_FILE_NAME = "Ridecell_Assignment"
private const val PREFS_IS_LOGIN = "is_login"
private const val PREFS_AUTH_TOKEN = "auth_token"
private const val PREFS_APP_LANGUAGE = "app_language"
private const val PREFS_USER = "logged_user"


class AppPreferences private constructor(context: Context) {

    companion object {
        private var instance: AppPreferences? = null
        fun getInstance(context: Context) = instance ?: AppPreferences(context)
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)


    var isAlreadyLogin: Boolean
        get() = if (sharedPreferences.contains(PREFS_IS_LOGIN)) {
            sharedPreferences.getBoolean(PREFS_IS_LOGIN, false)
        } else false
        set(value) = sharedPreferences.edit { it.putBoolean(PREFS_IS_LOGIN, value) }


    var sessionToken: String
        get() = sharedPreferences.getString(PREFS_AUTH_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit { it.putString(PREFS_AUTH_TOKEN, value) }


    fun loginUser(logInData: PojoLoginResponse?) {
        logInData?.let { loginData ->
            isAlreadyLogin = true
            sessionToken = loginData.authentication_token
            prefsLoggedUser = loginData.person
            AppGlobal.AUTH_TOKEN = loginData.authentication_token
        }
    }


    var prefsLoggedUser: PojoPersonData?
        get() = if (sharedPreferences.contains(PREFS_USER)) {
            Gson().fromJson(sharedPreferences.getString(PREFS_USER, ""), PojoPersonData::class.java)
        } else null
        set(value) = sharedPreferences.edit { it.putString(PREFS_USER, Gson().toJson(value)) }


    fun clearPrefs() = sharedPreferences.edit {
        AppGlobal.AUTH_TOKEN = ""
        it.clear()

    }


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

}