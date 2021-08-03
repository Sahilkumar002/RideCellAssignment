package com.example.ridecellassignment.views.fragments

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.utils.hideKeyboard
import com.example.ridecellassignment.views.activities.BaseActivity


abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {


    fun removeFragment() {
        if (isAdded) {
            parentFragmentManager.let {
                hideKeyboard()
                it.popBackStackImmediate()
            }
        }
    }

    fun showDialog() {
        (activity as BaseActivity?)?.showProgress()
    }

    fun hideDialog() {
        (activity as BaseActivity?)?.hideProgress()
    }

    fun handleError(error: PojoError?) {
        (activity as BaseActivity?)?.handleError(error)
    }


}