package com.example.ridecellassignment.views.activities

import android.os.Bundle
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.ActivityMainBinding
import com.example.ridecellassignment.utils.addFragmentWithoutStack
import com.example.ridecellassignment.views.fragments.LoginFragment

class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        LoginFragment().addFragmentWithoutStack(supportFragmentManager, getString(R.string.login))

    }
}