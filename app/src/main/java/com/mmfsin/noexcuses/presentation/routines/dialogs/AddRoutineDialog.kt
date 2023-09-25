package com.mmfsin.noexcuses.presentation.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogRoutineAddBinding
import com.mmfsin.noexcuses.presentation.routines.dialogs.interfaces.IRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoutineDialog(private val listener: IRoutineDialogListener) :
    BaseDialog<DialogRoutineAddBinding>() {

    private val viewModel: RoutineDialogViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogRoutineAddBinding.inflate(inflater)

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
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                if (title.isNotEmpty() && title.isNotBlank()) {
                    viewModel.addRoutine(title, description)
                } else {
                    Toast.makeText(requireContext(), "add title", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoutineDialogEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is RoutineDialogEvent.GetRoutine -> {}
                is RoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(listener: IRoutineDialogListener): AddRoutineDialog {
            return AddRoutineDialog(listener)
        }
    }
}