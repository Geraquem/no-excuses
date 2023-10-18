package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogMyRoutineEditBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMyRoutineDialog(val routineId: String, private val listener: IMyRoutineDialogListener) :
    BaseDialog<DialogMyRoutineEditBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    private var routine: Routine? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogMyRoutineEditBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getRoutine(routineId)
    }

    override fun setUI() {
        isCancelable = true
        binding.tvError.visibility = View.GONE
    }

    override fun setListeners() {
        binding.apply {
            btnDelete.setOnClickListener {
                routine?.let {
                    listener.deleteRoutine(it.id)
                    dismiss()
                }
            }

            btnEdit.setOnClickListener {
                routine?.let { r ->
                    val title = etTitle.text.toString()
                    val description = etDescription.text.toString()
                    if (title.isNotEmpty() && title.isNotBlank()) {
                        countDown { viewModel.editRoutine(r.id, title, description) }
                    } else binding.tvError.visibility = View.VISIBLE
                }
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
                is MyRoutineDialogEvent.GetMyRoutine -> {
                    routine = event.routine
                    binding.etTitle.setText(routine?.title)
                    binding.etDescription.setText(routine?.description)
                }
                is MyRoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IMyRoutineDialogListener): EditMyRoutineDialog {
            return EditMyRoutineDialog(id, listener)
        }
    }
}