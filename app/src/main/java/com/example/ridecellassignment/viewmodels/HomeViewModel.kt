package com.example.ridecellassignment.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ridecellassignment.repository.dataRepositories.HomeDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val homeRepo: HomeDataRepository by lazy { HomeDataRepository(prefs) }

    val successBlockedJob: LiveData<Boolean>
        get() = _successBlockedJob
    private val _successBlockedJob: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val successBlockedUser: LiveData<Boolean>
        get() = _successBlockedUser
    private val _successBlockedUser: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }




}