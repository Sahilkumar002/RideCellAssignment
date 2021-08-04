package com.example.ridecellassignment.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ridecellassignment.R
import com.example.ridecellassignment.databinding.FragmentLoginBinding
import com.example.ridecellassignment.databinding.FragmentSignUpBinding
import com.example.ridecellassignment.modals.RegisterBody
import com.example.ridecellassignment.utils.*
import com.example.ridecellassignment.viewmodels.LoginViewModel
import com.example.ridecellassignment.views.activities.HomeActivity

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private var viewBinding by autoCleared<FragmentSignUpBinding>()
    private val viewModel by viewModels<LoginViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentSignUpBinding.bind(view)

        observeViewModel()

        /* click listeners */
        viewBinding.btnSignUp.setOnClickListener { userSignUp() }

        /* after view created */
        viewBinding.etPassword.apply {
            doAfterTextChanged { text ->
                if (text != null) {
                    when {
                        text.isEmpty() || text.length <= 4 -> {
                            viewBinding.llPasswordStrength.hide()
                        }
                        text.length <= 6 -> {
                            showStrength(33, "Weak")
                        }
                        text.length <= 8 -> {
                            showStrength(66, "Moderate")
                        }
                        text.length > 8 -> {
                            showStrength(80, "Strong")
                        }
                    }
                }
            }
            setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) viewBinding.llPasswordStrength.hide() }
        }

    }

    private fun showStrength(strength: Int, strengthValue: String) {
        viewBinding.llPasswordStrength.show()
        viewBinding.progressBar.progress = strength
        viewBinding.tvPasswordStrength.text = String.format("Password Strength: %s", strengthValue)

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