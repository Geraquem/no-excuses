package com.mmfsin.noexcuses.presentation.maximums.dialogs.delete

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemDeleteBinding
import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.deletePointZero
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.toCompleteDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteMaxExerciseDialog(
    private val mDataId: String,
) : BaseDialog<DialogItemDeleteBinding>() {

    private val viewModel: DeleteMaxExerciseDialogViewModel by viewModels()

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
        viewModel.getMData(mDataId)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.apply {
            btnDelete.setOnClickListener {
                viewModel.deleteMData(mDataId)
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DeleteMaxExercisesDialogEvent.GetMData -> setMData(event.data)
                is DeleteMaxExercisesDialogEvent.Deleted -> {

                    dismiss()
                }

                is DeleteMaxExercisesDialogEvent.SWW -> error()
            }
        }
    }

    private fun setMData(data: MData) {
        binding.apply {
            tvTitle.text = getString(R.string.maximums_delete_title)
            tvText.text =
                getString(
                    R.string.maximums_delete_text,
                    data.weight.deletePointZero(),
                    data.date.toCompleteDate(requireContext())
                )
            tvAlert.isVisible = false
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(mDataId: String): DeleteMaxExerciseDialog {
            return DeleteMaxExerciseDialog(mDataId)
        }
    }
}