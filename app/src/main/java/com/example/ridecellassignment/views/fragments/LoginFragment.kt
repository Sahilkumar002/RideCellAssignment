package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentLoginBinding
import com.example.ridecellassignment.modals.LoginBody
import com.example.ridecellassignment.utils.addFragment
import com.example.ridecellassignment.utils.autoCleared
import com.example.ridecellassignment.utils.isValidEmail
import com.example.ridecellassignment.utils.showToast
import com.example.ridecellassignment.viewmodels.LoginViewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private var viewBinding by autoCleared<FragmentLoginBinding>()
    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLoginBinding.bind(view)
        observeViewModel()

        /* onClick Listeners */

        viewBinding.tvSignUp.setOnClickListener {
            SignUpFragment().addFragment(parentFragmentManager, getString(R.string.sign_up_instead))
        }

        viewBinding.btnLogin.setOnClickListener {
            if (checkForLoginValidation()) {
                val loginBody = LoginBody(
                    viewBinding.etEmail.text.toString().trim(),
                    viewBinding.etPassword.text.toString().trim()
                )
                viewModel.userLogin(loginBody)
            }
        }

    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showDialog() else hideDialog()
        }
        viewModel.errorLogin.observe(viewLifecycleOwner) { handleError(it) }
        viewModel.successLogin.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Success login")
            }
        }

    }

    private fun checkForLoginValidation(): Boolean {
        val emailString: String = viewBinding.etEmail.text.toString().trim()
        val passwordString: String = viewBinding.etPassword.text.toString().trim()
        when {
            emailString.isNullOrBlank() -> {
                showToast("Invalid Email")
                return false
            }
            !emailString.isValidEmail() -> {
                showToast("Invalid Email")
                return false

            }
            passwordString.isNullOrBlank() -> {
                showToast("Invalid Password")
                return false
            }
            passwordString.length < 7 -> {
                showToast("weak password")
                return false
            }

        }

        return true
    }

}