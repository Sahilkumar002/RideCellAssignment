package com.example.ridecellassignment.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val CONNECTION_BROADCAST = "local_network_broadcast"
const val CON_PARAM = "connection"


const val DATE_FORMAT_1 = "yyyy-MM-dd"
const val DATE_FORMAT_2 = "dd-MM-yyyy"
const val DATE_FORMAT_3 = "yyyy-MM-dd HH:mm"
const val DATE_FORMAT_4 = "dd-MM-yyyy HH:mm"
const val DATE_FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val DATE_FORMAT_6 = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_7 = "hh:mm a"
const val DATE_FORMAT_8 = "yyyy-MM-dd  hh:mm a"


val TZ_UTC = TimeZone.getTimeZone("UTC")
val MY_TYZ = TimeZone.getDefault()



/* retrofit extensions */
fun String.retrofitPart(partName: String, fileType: String?): MultipartBody.Part {
    val testFile: File = File(this)
    val mediaType = fileType?.toMediaTypeOrNull()
    val fileBody: RequestBody =
        testFile.asRequestBody(mediaType ?: ("image/png").toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, testFile.name, fileBody)
}

fun File.retrofitPart(partName: String, fileType: String?): MultipartBody.Part {
    val fileBody: RequestBody = this.asRequestBody(fileType?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, this.name, fileBody)
}


fun String.retrofitPart(): RequestBody {
    return this.toRequestBody("multipart/form-data".toMediaTypeOrNull())
}

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}



fun String.formatDate(): SimpleDateFormat = SimpleDateFormat(this, Locale.getDefault())

fun String?.toDate(format: String): Date? {
    return if (this.isNullOrBlank()) {
        null
    } else {
        format.formatDate().parse(this)
    }
}

fun Fragment.showToast(message: String?) {
    activity?.showToast(message)
}

fun Context.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}


inline fun <reified T : AppCompatActivity> Context.launchActivity(finish: Boolean? = null) {
    startActivity(Intent(this, T::class.java))
    finish?.let { if (it) (this as AppCompatActivity).finish() }

}

inline fun <reified T : AppCompatActivity> Context.launchActivityClearAll(finish: Boolean? = null) {
    startActivity(Intent(this, T::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    })
    finish?.let { if (it) (this as AppCompatActivity).finish() }

}


/* keyboard extensions */

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun AppCompatActivity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


val screenWidth = Resources.getSystem().displayMetrics.widthPixels

val screenHeight = Resources.getSystem().displayMetrics.heightPixels


inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    val drawable = ContextCompat.getDrawable(this.context, drawableRes)
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}


fun View.visible(boolean: Boolean) {
    visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.isViewVisible() = visibility == View.VISIBLE

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Int.asColor(context: Context) = ContextCompat.getColor(context, this)

fun Int.asDimen(context: Context) = context.resources.getDimensionPixelSize(this)

fun Int.asDrawable(context: Context) = ContextCompat.getDrawable(context, this)


inline fun <reified T> Gson.fromJsonTo(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)






