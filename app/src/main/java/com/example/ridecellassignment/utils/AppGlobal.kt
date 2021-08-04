package com.example.ridecellassignment.utils

import android.app.Application
import com.example.ridecellassignment.repository.local.AppPreferences

class AppGlobal : Application() {

    companion object {
        var AUTH_TOKEN: String = ""
    }

    private val prefs: AppPreferences by lazy { AppPreferences.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        AUTH_TOKEN = prefs.sessionToken

    }


}