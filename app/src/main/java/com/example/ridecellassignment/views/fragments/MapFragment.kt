package com.example.ridecellassignment.views.fragments

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentMapContainerBinding
import com.example.ridecellassignment.utils.autoCleared
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapFragment : BaseFragment(R.layout.fragment_map_container) {

    private var viewBinding by autoCleared<FragmentMapContainerBinding>()
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private val defaultZoom: Float = 15F
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMapContainerBinding.bind(view)
        mapFragment = parentFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)
    }

    private val mapReadyCallback = OnMapReadyCallback { asyncMap ->
        /* do activities after map is created*/
        googleMap = asyncMap
        setClickListenersNow()
        checkLocationPermission()
    }

    private fun setClickListenersNow() {


    }

    private fun checkLocationPermission() {


    }


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
                if (response.locationSettingsStates.isLocationPresent) {
                    updateMapView()
                    updateLocationPinOnMap()
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

    private fun updateMapView() {
        googleMap?.let { gMap ->
            try {
                updateLocationPinOnMap()
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = false
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }

        }

    }


    private fun updateLocationPinOnMap() {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    task.result?.let { loc ->
//                        googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                        googleMap?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(loc.latitude, loc.longitude), defaultZoom
                            )
                        )

                    }
                } else {
                    googleMap?.apply {
                        moveCamera(
                            CameraUpdateFactory.newLatLngZoom(defaultLocation, defaultZoom)
                        )
//                        uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


}