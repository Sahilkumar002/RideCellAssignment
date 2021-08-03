package com.example.ridecellassignment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ridecellassignment.repository.local.AppPreferences

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var firebaseToken: String = ""
    val isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    protected val prefs: AppPreferences by lazy { AppPreferences.getInstance(application) }

    fun logout() = prefs.clearPrefs()

    fun getLoggedUser() = prefs.prefsLoggedUser


}