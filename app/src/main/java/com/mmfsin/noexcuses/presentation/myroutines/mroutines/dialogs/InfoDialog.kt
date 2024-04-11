package com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs

import android.app.Dialog
import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogInfoBinding
import com.mmfsin.noexcuses.utils.animateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDialog : BaseDialog<DialogInfoBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        DialogInfoBinding.inflate(inflater)

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

    override fun setListeners() {
        binding.btnAccept.setOnClickListener { dismiss() }
    }

    companion object {
        fun newInstance(): InfoDialog {
            return InfoDialog()
        }
    }
}