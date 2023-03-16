package com.mmfsin.noexcuses.presentation.days.dialog.deleteday

import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDeletePhaseBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.interfaces.IConfigPhasesListener

class DeleteDayDialog(
    val day: Day,
    var delete: (day: Day) -> Unit
) : BaseDialog<DialogDeletePhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogDeletePhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.tvName.text = getString(R.string.delete_phase_confirm, day.name)
    }

    override fun setListeners() {
        binding.apply {
            btnCancel.setOnClickListener { dismiss() }
            btnDelete.setOnClickListener {
                delete(day)
                dismiss()
            }
        }
    }
}

