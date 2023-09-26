package com.mmfsin.noexcuses.presentation.routines.days.fragments

import android.app.Activity.INPUT_METHOD_SERVICE
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDayAddBinding
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDaysDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDaysConfigDialog(
    private val routineId: String, private val listener: IDaysDialogListener
) : BaseDialog<DialogDayAddBinding>() {

    private val viewModel: DayConfigViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogDayAddBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun setUI() {
        isCancelable = true
        binding.tvError.visibility = View.GONE
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val title = etTitle.text.toString()
                if (title.isNotEmpty() && title.isNotBlank()) {
                    closeKeyboard()
                    viewModel.addDay(routineId, title)
                } else tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DayConfigEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is DayConfigEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    private fun closeKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    companion object {
        fun newInstance(routineId: String, listener: IDaysDialogListener): AddDaysConfigDialog {
            return AddDaysConfigDialog(routineId, listener)
        }
    }
}