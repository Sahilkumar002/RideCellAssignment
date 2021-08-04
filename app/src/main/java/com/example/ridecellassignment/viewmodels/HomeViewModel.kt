package com.example.ridecellassignment.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.repository.dataRepositories.HomeDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val homeRepo: HomeDataRepository by lazy { HomeDataRepository(prefs) }

    val errorUpdateProfile: LiveData<PojoError>
        get() = _errorUpdateProfile
    private val _errorUpdateProfile: MutableLiveData<PojoError> by lazy { MutableLiveData() }

    val successUpdateProfile: LiveData<Boolean>
        get() = _successUpdateProfile
    private val _successUpdateProfile: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }


    fun isProfileAvailable() = prefs.prefsUserProfile != null

    fun profileData() = prefs.prefsUserProfile

    fun updateUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            prefs.prefsLoggedUser?.key?.let { usrkey ->
                homeRepo.updateProfile(usrkey) { result, error ->
                    if (result != null) {
                        _successUpdateProfile.postValue(true)
                    } else {
                        _errorUpdateProfile.postValue(error)
                    }

                }

            }

        }


    }


}