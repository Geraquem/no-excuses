package com.mmfsin.noexcuses.presentation.days.dialog.configday

import android.view.LayoutInflater
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogConfigPhaseBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.days.interfaces.IConfigDayListener

class ConfigDayDialog(
    val day: Day,
    var listener: IConfigDayListener
) : BaseDialog<DialogConfigPhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogConfigPhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.tvName.text = day.name
    }

    override fun setListeners() {
        binding.apply {
            btnEdit.setOnClickListener { listener.edit(day) }
            btnDelete.setOnClickListener { listener.delete(day) }
        }
    }
}

