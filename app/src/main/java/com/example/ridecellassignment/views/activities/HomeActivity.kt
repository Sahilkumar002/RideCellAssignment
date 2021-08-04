package com.example.ridecellassignment.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.ActivityHomeBinding
import com.example.ridecellassignment.viewmodels.HomeViewModel

class HomeActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val navController = findNavController(R.id.nav_host_ride_cell_home)
        viewBinding.bottomNavigationView.setupWithNavController(navController)

        /* update profile initially */
        updateProfile()

    }

    private fun updateProfile() {
        viewModel.updateUserProfile()
    }


}