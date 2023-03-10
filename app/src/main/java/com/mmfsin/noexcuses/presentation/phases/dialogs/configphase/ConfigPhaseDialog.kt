package com.mmfsin.noexcuses.presentation.phases.dialogs.configphase

import android.view.LayoutInflater
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogConfigPhaseBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.interfaces.IConfigPhasesListener

class ConfigPhaseDialog(
    val phase: Phase,
    var listener: IConfigPhasesListener
) : BaseDialog<DialogConfigPhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogConfigPhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.tvName.text = phase.name
    }

    override fun setListeners() {
        binding.apply {
            btnEdit.setOnClickListener { listener.edit(phase) }
            btnDelete.setOnClickListener { listener.delete(phase) }
        }
    }
}

