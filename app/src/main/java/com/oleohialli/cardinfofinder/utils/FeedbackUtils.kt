package com.oleohialli.cardinfofinder.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.oleohialli.cardinfofinder.R
import com.oleohialli.cardinfofinder.models.ErrorBody
import retrofit2.Response

private var snackbar: Snackbar? = null
private var dialog: AlertDialog? = null
private lateinit var builder: AlertDialog.Builder

fun successMessage(context: AppCompatActivity, layout: ConstraintLayout, message: String) {
    layout.hideKeyboard()
    snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT)
    val snackBarLayout = snackbar!!.view
    snackBarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.success_green))
    val textView = snackBarLayout.findViewById<TextView>(R.id.snackbar_text)
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0)
    textView.compoundDrawablePadding =
        context.resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)
    snackbar!!.show()
}

fun errorMessage(context: Context, layout: ConstraintLayout,
    message: String, actionTitle: String, action: View.OnClickListener) {
    layout.hideKeyboard()
    snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE)
    snackbar!!.setAction(actionTitle, action)
    snackbar!!.setActionTextColor(ContextCompat.getColor(context, android.R.color.white))
    val snackBarLayout = snackbar!!.view
    snackBarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_red))
    val textView = snackBarLayout.findViewById<TextView>(R.id.snackbar_text)
    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.error, 0, 0, 0)
    textView.compoundDrawablePadding =
        context.resources.getDimensionPixelOffset(R.dimen.snackbar_icon_padding)
    snackbar!!.show()
}

fun dismissSnackBar() {
    if (snackbar != null) {
        snackbar!!.dismiss()
    }
}

fun errorDialog(context: Context, layout: ConstraintLayout, dialogTitle: String, message: String, actionTitle: String,
                action: DialogInterface.OnClickListener) {
    layout.hideKeyboard()
    builder = AlertDialog.Builder(context)
    builder.setTitle(dialogTitle)
    //builder.setIcon(R.drawable.error)
    builder.setMessage(message)
    builder.setPositiveButton(actionTitle, action)
    dialog = builder.create()
    dialog!!.show()
}

fun dismissDialog() {
    if (dialog != null) {
        dialog!!.dismiss()
    }
}

fun showLoader(activity: AppCompatActivity,
               appLoader: SpinKitView) {
    appLoader.visibility = View.VISIBLE
    appLoader.hideKeyboard()
    //dismissSnackBar()
    dismissDialog()
    activity.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun hideLoader(activity: AppCompatActivity, appLoader: SpinKitView) {
    appLoader.visibility = View.GONE
    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun View.hideKeyboard() {
    val hideAction =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    hideAction.hideSoftInputFromWindow(windowToken, 0)
}

fun<T> serializeErrorMessage(activity: AppCompatActivity,
                             response: Response<T>,
                             constraintLayout: ConstraintLayout,
                             dismissDialog: DialogInterface.OnClickListener){
    try {
        val errorMessage = Gson().fromJson(response.errorBody()?.charStream(),
            ErrorBody::class.java).serverMessage ?: activity.resources.getString(R.string.problem_text)
        //errorDialog(activity, constraintLayout, errorMessage, activity.resources.getString(R.string.dismiss_text),)
    }
    catch (e: JsonSyntaxException) {
        showErrorMessage(activity, constraintLayout, activity.resources.getString(R.string.problem_text),
            DialogInterface.OnClickListener { dialog, which -> dismissDialog() }, activity.resources.getString(R.string.dismiss_text))
    }
}

fun showErrorMessage(activity: AppCompatActivity,
                     constraintLayout: ConstraintLayout,
                     message: String,
                     action: DialogInterface.OnClickListener,
                     actionTitle: String) {
    errorDialog(activity, constraintLayout, activity.resources.getString(R.string.problem_text), message, actionTitle, action)
}
