package com.example.ridecellassignment.views.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.ActivityHomeBinding
import com.example.ridecellassignment.databinding.ActivityMainBinding
import com.example.ridecellassignment.utils.addFragmentWithoutStack
import com.example.ridecellassignment.utils.launchActivity
import com.example.ridecellassignment.viewmodels.HomeViewModel
import com.example.ridecellassignment.viewmodels.LoginViewModel
import com.example.ridecellassignment.views.fragments.LoginFragment

class HomeActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getLoggedUser()?.display_name?.let {
            viewBinding.tvWelcome.text = it
        }


    }
}