package com.mmfsin.noexcuses.presentation.days.dialog.newday

import android.view.LayoutInflater
import android.widget.Toast
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogNewDayBinding
import com.mmfsin.noexcuses.domain.models.Day

class NewDayDialog(var day: Day?, var phaseId: String, var completed: (id: String) -> Unit) :
    BaseDialog<DialogNewDayBinding>(), NewDayView {

    private val presenter by lazy { NewDayPresenter(this) }

    override fun inflateView(inflater: LayoutInflater) = DialogNewDayBinding.inflate(inflater)

    override fun setUI() {
        binding.apply {
            day?.let { day ->
                tvTitle.text = getString(R.string.edit_phase_title, day.name)
                etName.hint = day.name
            } ?: run {
                tvTitle.text = getString(R.string.new_day_title)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            cancel.setOnClickListener { dismiss() }
            save.setOnClickListener {
                val name = etName.text.toString()
                if (name.isNotEmpty()) {
                    val id = day?.id ?: run { null }
                    presenter.save(id, phaseId, name)
                }
            }
        }
    }

    override fun savedInRealm() {
        completed(phaseId)
        dismiss()
    }

    override fun sww() {
        Toast.makeText(activity?.applicationContext, "SWW", Toast.LENGTH_SHORT).show()
    }
}