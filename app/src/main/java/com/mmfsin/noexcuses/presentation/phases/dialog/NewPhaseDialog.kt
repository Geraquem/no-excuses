package com.mmfsin.noexcuses.presentation.phases.dialog

import android.view.LayoutInflater
import android.widget.Toast
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogNewPhaseBinding

class NewPhaseDialog(var completed: () -> Unit) : BaseDialog<DialogNewPhaseBinding>(), NewPhaseView {

    private val presenter by lazy { NewPhasePresenter(this) }

    override fun inflateView(inflater: LayoutInflater): DialogNewPhaseBinding =
        DialogNewPhaseBinding.inflate(inflater)

    override fun setListeners() {
        binding.apply {
            cancel.setOnClickListener { dismiss() }
            save.setOnClickListener {
                val name = etName.text.toString()
                val description = etDescription.text.toString()
                if (name.isNotEmpty()) {
                    presenter.save(name, description)
                }
            }
        }
    }

    override fun savedInRealm() {
        Toast.makeText(activity?.applicationContext, "Guardado", Toast.LENGTH_SHORT).show()
        completed()
        dismiss()
    }

    override fun sww() {
        Toast.makeText(activity?.applicationContext, "SWW", Toast.LENGTH_SHORT).show()
    }
}