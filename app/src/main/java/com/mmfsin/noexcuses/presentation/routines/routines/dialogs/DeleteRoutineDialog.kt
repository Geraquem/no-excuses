package com.mmfsin.noexcuses.presentation.routines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogRoutineDeleteBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.routines.routines.interfaces.IRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteRoutineDialog(val id: String, private val listener: IRoutineDialogListener) :
    BaseDialog<DialogRoutineDeleteBinding>() {

    private val viewModel: RoutineDialogViewModel by viewModels()

    private var routine: Routine? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogRoutineDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getRoutine(id)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener { routine?.let { viewModel.deleteRoutine(it.id) } }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is RoutineDialogEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is RoutineDialogEvent.GetRoutine -> {
                    routine = event.routine
                    binding.tvText.text = getString(R.string.routines_delete_text, routine?.title)
                }
                is RoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IRoutineDialogListener): DeleteRoutineDialog {
            return DeleteRoutineDialog(id, listener)
        }
    }
}