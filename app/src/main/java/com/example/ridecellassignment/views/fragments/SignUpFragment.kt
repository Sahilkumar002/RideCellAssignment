package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentLoginBinding
import com.example.ridecellassignment.databinding.FragmentSignUpBinding
import com.example.ridecellassignment.modals.RegisterBody
import com.example.ridecellassignment.utils.autoCleared
import com.example.ridecellassignment.utils.isValidEmail
import com.example.ridecellassignment.utils.launchActivity
import com.example.ridecellassignment.utils.showToast
import com.example.ridecellassignment.viewmodels.LoginViewModel
import com.example.ridecellassignment.views.activities.HomeActivity

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private var viewBinding by autoCleared<FragmentSignUpBinding>()
    private lateinit var viewModel: LoginViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSignUpBinding.bind(view)

        observeViewModel()

        /* click listeners */

        viewBinding.btnSignUp.setOnClickListener { userSignUp() }


    }

    private fun userSignUp() {
        if (checkForValidation()) {
            viewModel.userSignUp(
                RegisterBody(
                    viewBinding.etUserName.text.toString().trim(),
                    viewBinding.etEmail.text.toString().trim(),
                    viewBinding.etPassword.text.toString().trim()
                )
            )
        }

    }

    private fun checkForValidation(): Boolean {
        when {
            viewBinding.etUserName.text.toString().trim().isNullOrBlank() -> {
                showToast("Enter User Name")
                return false
            }
            viewBinding.etEmail.text.toString().trim().isNullOrBlank() -> {
                showToast("Enter Email address")
                return false
            }
            !viewBinding.etEmail.text.toString().trim().isValidEmail() -> {
                showToast("Enter Valid Email")
                return false
            }
            viewBinding.etPassword.text.toString().trim().isNullOrBlank() -> {
                showToast("Enter password")
                return false
            }
            viewBinding.etPassword.text.toString().trim().length < 7 -> {
                showToast("Password too weak")
                return false
            }
            !viewBinding.etPassword.text.toString().trim()
                .equals(viewBinding.etConfirmPassword.text.toString().trim(), true) -> {
                showToast("Password doesn't match")
                return false
            }
            else -> return true
        }
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showDialog() else hideDialog()
        }
        viewModel.errorRegister.observe(viewLifecycleOwner) { handleError(it) }
        viewModel.successRegister.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Success Register")
                launchActivity(HomeActivity::class.java, true)
            }
        }
    }

}