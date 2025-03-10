package com.mmfsin.noexcuses.presentation.menu.dialogs.unpin

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogAddRoutineBinding
import com.mmfsin.noexcuses.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnpinRoutineDialog(
    private val routineName: String,
    private val add: () -> Unit
) : BaseDialog<DialogAddRoutineBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogAddRoutineBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvText.text = getString(R.string.df_routines_add_description, routineName)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAdd.setOnClickListener {
                add()
                dismiss()
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    companion object {
        fun newInstance(routineName: String, add: () -> Unit): UnpinRoutineDialog {
            return UnpinRoutineDialog(routineName, add)
        }
    }
}