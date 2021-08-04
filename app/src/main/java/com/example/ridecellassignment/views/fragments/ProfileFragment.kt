package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentProfileBinding
import com.example.ridecellassignment.utils.GeneralFunctions
import com.example.ridecellassignment.utils.autoCleared
import com.example.ridecellassignment.utils.launchActivity
import com.example.ridecellassignment.viewmodels.HomeViewModel
import com.example.ridecellassignment.views.activities.BaseActivity
import com.example.ridecellassignment.views.activities.MainActivity
import java.util.*

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private var viewBinding by autoCleared<FragmentProfileBinding>()
    private val viewModel by viewModels<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentProfileBinding.bind(view)
        observeViewModel()

        /* click listeners */
        viewBinding.btnLogout.setOnClickListener {
            (activity as BaseActivity?)?.logout {
                viewModel.logout()
                launchActivity(MainActivity::class.java, true)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.successUpdateProfile.observe(viewLifecycleOwner) {
            if (it) {
                updateDataToView()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkForUpdateProfile()
    }

    private fun checkForUpdateProfile() {
        if (!viewModel.isProfileAvailable()) {
            viewModel.updateUserProfile()
        } else {
            updateDataToView()
        }
    }

    private fun updateDataToView() {
        viewModel.profileData()?.let {
            viewBinding.tvUserName.text = it.display_name
            viewBinding.tvAge.text = String.format(
                Locale.getDefault(),
                "%s old",
                GeneralFunctions.calculateDays(it.created_at)
            )
        }

    }

}