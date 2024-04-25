package com.mmfsin.noexcuses.presentation.myroutines.myroutines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemAddBinding
import com.mmfsin.noexcuses.presentation.myroutines.myroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown300
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutineAddDialog(private val listener: IMyRoutineListener) :
    BaseDialog<DialogItemAddBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogItemAddBinding.inflate(inflater)

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
        binding.apply {
            tvTitle.text = getString(R.string.routines_add_top_text)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                if (title.isNotEmpty() && title.isNotBlank()) {
                    countDown300 { viewModel.addRoutine(title, description) }
                } else {
                    tilTitle.error = getString(R.string.routines_add_title_error)
                    tilTitle.isErrorEnabled = true
                }
            }

            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyRoutineDialogEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }

                is MyRoutineDialogEvent.GetMyRoutine -> {}
                is MyRoutineDialogEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(listener: IMyRoutineListener): MyRoutineAddDialog {
            return MyRoutineAddDialog(listener)
        }
    }
}