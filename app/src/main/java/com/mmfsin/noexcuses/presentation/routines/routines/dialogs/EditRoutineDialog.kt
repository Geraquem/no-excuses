package com.mmfsin.noexcuses.presentation.routines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogRoutineEditBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.routines.routines.interfaces.IRoutineDialogListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRoutineDialog(val routineId: String, private val listener: IRoutineDialogListener) :
    BaseDialog<DialogRoutineEditBinding>() {

    private val viewModel: RoutineDialogViewModel by viewModels()

    private var routine: Routine? = null

    override fun inflateView(inflater: LayoutInflater) = DialogRoutineEditBinding.inflate(inflater)

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
                is RoutineDialogEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }
                is RoutineDialogEvent.GetRoutine -> {
                    routine = event.routine
                    binding.etTitle.setText(routine?.title)
                    binding.etDescription.setText(routine?.description)
                }
                is RoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IRoutineDialogListener): EditRoutineDialog {
            return EditRoutineDialog(id, listener)
        }
    }
}