package com.mmfsin.noexcuses.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mmfsin.noexcuses.base.dialog.ErrorDialog

//fun FragmentActivity.shouldShowInterstitial(position: Int) =
//    (this as MainActivity).showInterstitial(position)

fun FragmentActivity.showErrorDialog(goBack: Boolean = true) {
    val dialog = ErrorDialog(goBack)
    this.let { dialog.show(it.supportFragmentManager, "") }
}

fun isKeyboardVisible(view: View): Boolean {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.isAcceptingText
}

fun Activity.closeKeyboard() {
    this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun DialogFragment.closeKeyboard() {
    val inputMethodManager =
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocusView = dialog?.window?.currentFocus
    if (currentFocusView != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
    }
}

fun FragmentActivity?.showFragmentDialog(dialog: DialogFragment) =
    this?.let { dialog.show(it.supportFragmentManager, "") }

fun FragmentManager.showFragmentDialog(dialog: DialogFragment) = dialog.show(this, "")

fun Dialog.animateDialog() {
    val dialogView = this.window?.decorView
    dialogView?.let {
        it.scaleX = 0f
        it.scaleY = 0f
        val scaleXAnimator = ObjectAnimator.ofFloat(it, View.SCALE_X, 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(it, View.SCALE_Y, 1f)
        AnimatorSet().apply {
            playTogether(scaleXAnimator, scaleYAnimator)
            duration = 400
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}

fun countDown300(action: () -> Unit) {
    object : CountDownTimer(300, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            action()
        }
    }.start()
}

fun countDown(millis: Long, action: () -> Unit) {
    object : CountDownTimer(millis, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            action()
        }
    }.start()
}

fun View.animateY(pos: Float, duration: Long) =
    this.animate().translationY(pos).setDuration(duration)

fun View.animateX(pos: Float, duration: Long) =
    this.animate().translationX(pos).setDuration(duration)

fun Double?.formatTime(): String? {
    return this?.let { d ->
        String.format("%.2f", d).replace(",", ":").replace(":00", "")
    } ?: run { null }
}

fun Double?.deletePointZero(): String {
    val formatted = this.toString()
    return if (formatted.endsWith(".0")) {
        formatted.replace(".0", "")
    } else formatted
}

fun <T> Bundle.getBundleParcelableArgs(identifier: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(identifier, clazz)
    } else @Suppress("DEPRECATION") this.getParcelable(identifier)
}

fun <T1 : Any, T2 : Any, R : Any> checkNotNulls(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}