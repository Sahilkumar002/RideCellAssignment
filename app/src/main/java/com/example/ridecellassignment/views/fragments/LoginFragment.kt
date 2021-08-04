package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentLoginBinding
import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.utils.*
import com.example.ridecellassignment.viewmodels.LoginViewModel
import com.example.ridecellassignment.views.activities.HomeActivity

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var viewBinding by autoCleared<FragmentLoginBinding>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLoginBinding.bind(view)
        observeViewModel()

        /* onClick Listeners */

        viewBinding.tvSignUp.setOnClickListener {
            SignUpFragment().addFragment(parentFragmentManager, getString(R.string.sign_up_instead))
        }

        viewBinding.btnLogin.setOnClickListener { userSignIn() }

    }

    private fun userSignIn() {
        if (checkForLoginValidation()) {
            val loginBody = LoginBody(
                viewBinding.etEmail.text.toString().trim(),
                viewBinding.etPassword.text.toString().trim()
            )
            viewModel.userLogin(loginBody)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showDialog() else hideDialog()
        }
        viewModel.errorLogin.observe(viewLifecycleOwner) { handleError(it) }
        viewModel.successLogin.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Success login")
                launchActivity(HomeActivity::class.java, true)
            }
        }

    }

    private fun checkForLoginValidation(): Boolean {
        when {
            viewBinding.etEmail.text.toString().trim().isNullOrBlank() -> {
                showToast("Invalid Email")
                return false
            }
            !viewBinding.etEmail.text.toString().trim().isValidEmail() -> {
                showToast("Invalid Email")
                return false

            }
            viewBinding.etPassword.text.toString().trim().isNullOrBlank() -> {
                showToast("Invalid Password")
                return false
            }
            viewBinding.etPassword.text.toString().trim().length < 7 -> {
                showToast("weak password")
                return false
            }
            else -> return true
        }
    }

}