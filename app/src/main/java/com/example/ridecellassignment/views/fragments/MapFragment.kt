package com.example.ridecellassignment.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentMapContainerBinding
import com.example.ridecellassignment.utils.GeneralFunctions
import com.example.ridecellassignment.utils.autoCleared
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapFragment : BaseLocationFragment(R.layout.fragment_map_container) {

    private var viewBinding by autoCleared<FragmentMapContainerBinding>()
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private val defaultZoom: Float = 10F
    private val myLocationZoom: Float = 15F
    private var isMyLocation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMapContainerBinding.bind(view)

        /* click Listeners */
        viewBinding.ivMyLocation.setOnClickListener {
            isMyLocation = true
            checkForLocationPermissions()
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)
    }

    private val mapReadyCallback = OnMapReadyCallback { asyncMap ->
        /* do activities after map is created*/
        googleMap = asyncMap
        isMyLocation = false
        checkForLocationPermissions()
        addCustomMarkersToMap()


    }

    private fun addCustomMarkersToMap() {
        for (location in GeneralFunctions.getMarkerLocations()) {
            val marker = MarkerOptions().apply {
                position(LatLng(location.latitude, location.longitude))
                title(location.cityName)
                icon(bitMapFromVector(requireContext(), location.icon))
            }
            googleMap?.addMarker(marker)
        }

    }


    override fun dragCameraToLocation() {

        googleMap?.let {
            it.addMarker(
                MarkerOptions().position(LatLng(latitude, longitude)).title("Current Location")
            )

            val cameraUpdate =
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(latitude, longitude), if (isMyLocation) myLocationZoom else defaultZoom
                )
            it.animateCamera(cameraUpdate)
        }


    }


    private fun bitMapFromVector(
        context: Context, @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}