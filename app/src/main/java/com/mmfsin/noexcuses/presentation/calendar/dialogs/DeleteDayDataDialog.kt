package com.mmfsin.noexcuses.presentation.calendar.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogCalendarDayDeleteBinding
import com.mmfsin.noexcuses.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDayDataDialog(val delete: () -> Unit) : BaseDialog<DialogCalendarDayDeleteBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogCalendarDayDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnDelete.setOnClickListener {
                delete()
                dismiss()
            }
            btnDelete.setOnClickListener {
                delete()
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(delete: () -> Unit): DeleteDayDataDialog {
            return DeleteDayDataDialog(delete)
        }
    }
}