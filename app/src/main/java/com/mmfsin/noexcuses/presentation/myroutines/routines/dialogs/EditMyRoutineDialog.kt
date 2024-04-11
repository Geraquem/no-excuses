package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogMyRoutineEditBinding
import com.mmfsin.noexcuses.domain.models.MyRoutine
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMyRoutineDialog(val routineId: String, private val listener: IMyRoutineListener) :
    BaseDialog<DialogMyRoutineEditBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    private var myRoutine: MyRoutine? = null

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
                myRoutine?.let {
                    listener.deleteRoutine(it.id)
                    dismiss()
                }
            }

            btnEdit.setOnClickListener {
                myRoutine?.let { r ->
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
                    myRoutine = event.myRoutine
                    binding.etTitle.setText(myRoutine?.title)
                    binding.etDescription.setText(myRoutine?.description)
                }

                is MyRoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IMyRoutineListener): EditMyRoutineDialog {
            return EditMyRoutineDialog(id, listener)
        }
    }
}