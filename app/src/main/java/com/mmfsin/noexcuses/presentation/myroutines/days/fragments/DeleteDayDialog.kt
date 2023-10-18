package com.mmfsin.noexcuses.presentation.myroutines.days.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDayDeleteBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.days.interfaces.IDaysDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDayDialog(private val dayId: String, private val listener: IDaysDialogListener) :
    BaseDialog<DialogDayDeleteBinding>() {

    private val viewModel: DayConfigViewModel by viewModels()

    private var day: Day? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogDayDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDay(dayId)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener { day?.let { viewModel.deleteDay(it.id) } }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DayConfigEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is DayConfigEvent.GetDay -> {
                    day = event.day
                    binding.tvText.text = getString(R.string.routines_delete_text, day?.title)
                }
                is DayConfigEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IDaysDialogListener): DeleteDayDialog {
            return DeleteDayDialog(id, listener)
        }
    }
}