package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogMyRoutineAddBinding
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMyRoutineDialog(private val listener: IMyRoutineDialogListener) :
    BaseDialog<DialogMyRoutineAddBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogMyRoutineAddBinding.inflate(inflater)

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
                val description = etDescription.text.toString()
                if (title.isNotEmpty() && title.isNotBlank()) {
                    countDown { viewModel.addRoutine(title, description) }
                } else tvError.visibility = View.VISIBLE
            }
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
                is MyRoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(listener: IMyRoutineDialogListener): AddMyRoutineDialog {
            return AddMyRoutineDialog(listener)
        }
    }
}