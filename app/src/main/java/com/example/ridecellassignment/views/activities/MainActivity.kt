package com.example.ridecellassignment.views.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.ActivityMainBinding
import com.example.ridecellassignment.utils.addFragmentWithoutStack
import com.example.ridecellassignment.utils.launchActivity
import com.example.ridecellassignment.viewmodels.LoginViewModel
import com.example.ridecellassignment.views.fragments.LoginFragment

class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (viewModel.isAlreadyLogin()) {
            launchActivity<HomeActivity>(true)
        } else {
            LoginFragment().addFragmentWithoutStack(
                supportFragmentManager, getString(R.string.login)
            )

        }


    }
}