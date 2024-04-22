package com.mmfsin.noexcuses.presentation.myroutines.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDialog : BaseDialog<DialogInfoBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogInfoBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

    override fun setUI() {
        isCancelable = true
        binding.apply {
            item.image.tvNumOfDays.text = getString(R.string.first_dialog_routine_days)
            item.tvTitle.text = getString(R.string.first_dialog_routine_title)
            item.tvDescription.text = getString(R.string.first_dialog_routine_description)
        }
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            btnAccept.setOnClickListener { dismiss() }
        }
    }
}