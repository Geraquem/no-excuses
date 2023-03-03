package com.mmfsin.noexcuses.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import java.lang.Exception

abstract class BaseDialog<VB : ViewBinding> : DialogFragment() {

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding
        get() = _binding as VB

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = this.activity ?: return super.onCreateDialog(savedInstanceState)
        val dialog = Dialog(activity)
        _binding = inflateView(activity.layoutInflater)
        dialog.setContentView(binding.root)
        setCustomViewDialog(dialog)
        dialog.show()
        return dialog
    }

    protected abstract fun inflateView(inflater: LayoutInflater): VB

    private fun setCustomViewDialog(dialog: Dialog) {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.9
        dialog.window?.setLayout(width.toInt(), LayoutParams.WRAP_CONTENT)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragment = manager.findFragmentByTag(tag)
        val ft = manager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment)
            ft.commitAllowingStateLoss()
        }

        try {
            super.show(manager, tag)
        } catch (e: Exception) {
            Log.e(BaseDialog::class.java.name, e.stackTraceToString())
        }
    }

    override fun onStart() {
        super.onStart()
        setUI()
        setListeners()
    }

    open fun setUI() {}
    open fun setListeners() {}
}