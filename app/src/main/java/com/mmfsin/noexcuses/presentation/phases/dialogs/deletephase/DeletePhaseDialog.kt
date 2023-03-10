package com.mmfsin.noexcuses.presentation.phases.dialogs.deletephase

import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDeletePhaseBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.interfaces.IConfigPhasesListener

class DeletePhaseDialog(
    val phase: Phase,
    var delete: (phase: Phase) -> Unit
) : BaseDialog<DialogDeletePhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogDeletePhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.tvName.text = getString(R.string.delete_phase_confirm, phase.name)
    }

    override fun setListeners() {
        binding.apply {
            btnCancel.setOnClickListener { dismiss() }
            btnDelete.setOnClickListener {
                delete(phase)
                dismiss()
            }
        }
    }
}

