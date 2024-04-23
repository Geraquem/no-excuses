package com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemEditBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown300
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutineEditDialog(val routineId: String, private val listener: IMyRoutineListener) :
    BaseDialog<DialogItemEditBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    private var routine: Routine? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogItemEditBinding.inflate(inflater)

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
        binding.apply {
            tvTitle.text = getString(R.string.routines_edit_top_text)
        }
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
                        countDown300 { viewModel.editRoutine(r.id, title, description) }
                    } else {
                        tilTitle.error = getString(R.string.routines_add_title_error)
                        tilTitle.isErrorEnabled = true
                    }
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

                is MyRoutineDialogEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IMyRoutineListener): MyRoutineEditDialog {
            return MyRoutineEditDialog(id, listener)
        }
    }
}