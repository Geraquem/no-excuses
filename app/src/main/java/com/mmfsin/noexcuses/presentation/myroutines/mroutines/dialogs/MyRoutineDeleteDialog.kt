package com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemDeleteBinding
import com.mmfsin.noexcuses.domain.models.MyRoutine
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRoutineDeleteDialog(val id: String, private val listener: IMyRoutineListener) :
    BaseDialog<DialogItemDeleteBinding>() {

    private val viewModel: MyRoutineDialogViewModel by viewModels()

    private var myRoutine: MyRoutine? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogItemDeleteBinding.inflate(inflater)

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
        binding.apply {
            tvTitle.text = getString(R.string.routines_delete_top_text)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener { myRoutine?.let { viewModel.deleteRoutine(it.id) } }
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
                    binding.tvText.text = getString(R.string.routines_delete_text, myRoutine?.title)
                }
                is MyRoutineDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IMyRoutineListener): MyRoutineDeleteDialog {
            return MyRoutineDeleteDialog(id, listener)
        }
    }
}