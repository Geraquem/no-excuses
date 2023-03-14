package com.mmfsin.noexcuses.presentation.dayexercises.dialogs.deleteday

import android.view.LayoutInflater
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDeletePhaseBinding
import com.mmfsin.noexcuses.domain.models.ComboModel

class DeleteDayExerciseDialog(
    val name: String,
    private val combo: ComboModel,
    var delete: (comboModel: ComboModel) -> Unit
) : BaseDialog<DialogDeletePhaseBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogDeletePhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.tvName.text = getString(R.string.delete_day_exercise_confirm, name)
    }

    override fun setListeners() {
        binding.apply {
            btnCancel.setOnClickListener { dismiss() }
            btnDelete.setOnClickListener {
                delete(combo)
                dismiss()
            }
        }
    }
}

