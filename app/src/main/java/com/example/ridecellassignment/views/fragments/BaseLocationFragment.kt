package com.example.ridecellassignment.views.fragments

import android.Manifest
import android.content.DialogInterface
import android.content.IntentSender
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ridecellassignment.R
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.utils.hideKeyboard
import com.example.ridecellassignment.utils.showToast
import com.example.ridecellassignment.views.activities.BaseActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest


abstract class BaseLocationFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected var latitude = 30.7046
    protected var longitude = 76.7179

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    private val handleLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                checkGPSStatus()
            } else {
                showToast(getString(R.string.location_rt_permissions))
            }
        }


    fun checkForLocationPermissions() {
        when {
            isLocationGranted() -> checkGPSStatus()
            shouldShowRequestPermissionRationale(locationPermission) -> {
                showAlertDialog(getString(R.string.location_rt_permissions), { dialog, _ ->
                    dialog.dismiss()
                    handleLocationPermission.launch(locationPermission)
                })
            }
            else -> handleLocationPermission.launch(locationPermission)

        }
    }


    private fun isLocationGranted(): Boolean =
        PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        )


    private fun checkGPSStatus() {
        val setClient = LocationServices.getSettingsClient(requireActivity())
        val builder =
            LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }).setAlwaysShow(true)

        val task = setClient.checkLocationSettings(builder.build())
        task.apply {
            addOnSuccessListener { response ->
                if (response?.locationSettingsStates?.isLocationPresent == true) {
                    updateLocation()
                }
            }
            addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(requireActivity(), 9195)
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }

            }
        }

    }

    private fun updateLocation() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        task.result?.let { loc ->
                            latitude = loc.latitude
                            longitude = loc.longitude
                            dragCameraToLocation()
                            Log.e("current", "$latitude  ----   $longitude")
                        }
                    } else {
                        /* drag to default location */
                        dragCameraToLocation()
                    }

                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }

    }

    abstract fun dragCameraToLocation()


    private fun showAlertDialog(
        message: String, okListener: DialogInterface.OnClickListener,
        cancelListener: DialogInterface.OnClickListener? = null
    ) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay), okListener)
            .setNegativeButton(getString(R.string.cancel_text), cancelListener)
            .create()
            .show()
    }


}