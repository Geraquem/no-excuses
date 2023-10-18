package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogFirstExplicationBinding
import com.mmfsin.noexcuses.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitInfoDialog : BaseDialog<DialogFirstExplicationBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogFirstExplicationBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            item.image.tvNumOfDays.text = getString(R.string.first_dialog_routine_days)
            item.tvTitle.text = getString(R.string.first_dialog_routine_title)
            item.tvDescription.text = getString(R.string.first_dialog_routine_description)
        }
    }

    override fun setListeners() {}

    companion object {
        fun newInstance(): InitInfoDialog {
            return InitInfoDialog()
        }
    }
}