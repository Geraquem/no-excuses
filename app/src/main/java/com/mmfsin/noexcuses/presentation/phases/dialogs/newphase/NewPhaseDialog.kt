package com.mmfsin.noexcuses.presentation.phases.dialogs.newphase

import android.view.LayoutInflater
import android.widget.Toast
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogNewPhaseBinding
import com.mmfsin.noexcuses.domain.models.Phase

class NewPhaseDialog(var phase: Phase?, var completed: () -> Unit) :
    BaseDialog<DialogNewPhaseBinding>(), NewPhaseView {

    private val presenter by lazy { NewPhasePresenter(this) }

    override fun inflateView(inflater: LayoutInflater) = DialogNewPhaseBinding.inflate(inflater)

    override fun setUI() {
        binding.apply {
            phase?.let { phase ->
                /** EDIT */
                tvTitle.text = getString(R.string.edit_phase_title, phase.name)
                etName.setText(phase.name)
                phase.description?.let { etDescription.setText(phase.description) }
            } ?: run {
                /** NEW */
                tvTitle.text = getString(R.string.new_phase_title)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            cancel.setOnClickListener { dismiss() }
            save.setOnClickListener {
                val name = etName.text.toString()
                val description = etDescription.text.toString()
                if (name.isNotEmpty()) {
                    val id = phase?.id ?: run { null }
                    presenter.save(id, name, description)
                }
            }
        }
    }

    override fun savedInRealm() {
        completed()
        dismiss()
    }

    override fun sww() {
        Toast.makeText(activity?.applicationContext, "SWW", Toast.LENGTH_SHORT).show()
    }
}