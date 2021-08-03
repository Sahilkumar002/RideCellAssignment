package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentLoginBinding
import com.example.ridecellassignment.utils.addFragment
import com.example.ridecellassignment.utils.autoCleared
import com.example.ridecellassignment.utils.showToast

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var viewBinding by autoCleared<FragmentLoginBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLoginBinding.bind(view)

        /* onClick Listeners */

        viewBinding.tvSignUp.setOnClickListener {
            SignUpFragment().addFragment(parentFragmentManager, getString(R.string.sign_up_instead))
        }

        viewBinding.btnLogin.setOnClickListener {
            showToast("Login User")
        }

    }

}