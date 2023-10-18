package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogMyRoutineDeleteBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteMyRoutineDialog(val id: String, private val listener: IMyRoutineDialogListener) :
    BaseDialog<DialogMyRoutineDeleteBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    private var routine: Routine? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogMyRoutineDeleteBinding.inflate(inflater)

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
                is MyRoutineDialogEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is MyRoutineDialogEvent.GetMyRoutine -> {
                    routine = event.routine
                    binding.tvText.text = getString(R.string.routines_delete_text, routine?.title)
                }
                is MyRoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IMyRoutineDialogListener): DeleteMyRoutineDialog {
            return DeleteMyRoutineDialog(id, listener)
        }
    }
}