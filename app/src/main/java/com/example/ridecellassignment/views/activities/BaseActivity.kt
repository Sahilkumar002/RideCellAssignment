package com.example.ridecellassignment.views.activities

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ridecellassignment.R
import com.example.ridecellassignment.modals.PojoError
import com.example.ridecellassignment.utils.launchActivity
import com.example.ridecellassignment.utils.showToast
import java.net.UnknownHostException

abstract class BaseActivity : AppCompatActivity() {

    private val progressDialog: Dialog by lazy {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_progress)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
        }
    }

    fun handleError(error: PojoError?) {
        error?.let { errorObj ->
            when {
                !errorObj.errorString.isNullOrBlank() -> showToast(errorObj.errorString)
                errorObj.exception != null -> when (errorObj.exception) {
                    is UnknownHostException -> showToast(getString(R.string.no_internet_connection))
                    else -> showToast(errorObj.exception?.localizedMessage)
                }
            }
            /* session expire error */
            if (errorObj.errorCode == 401) {
                launchActivity<MainActivity>(true)
            }


        }
    }

    fun showProgress() {
        if (!isDestroyed) {
            if (!progressDialog.isShowing) progressDialog.show()
        }
    }

    fun hideProgress() {
        if (!isDestroyed) {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    fun logout(clearPrefs: (clear: Boolean) -> Unit) {
        AlertDialog.Builder(this).apply {
            title = getString(R.string.logout)
            setMessage(getString(R.string.logout_warning))
            setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                dialog.dismiss()
                clearPrefs(true)
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            setCancelable(false)
            show()
        }
    }


}