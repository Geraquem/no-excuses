package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.delete

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemDeleteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCreatedExerciseDialog(
    val exerciseId: String,
    val deleted: () -> Unit
) : BaseDialog<DialogItemDeleteBinding>() {

    private val viewModel: DeleteCreateExerciseDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getCreatedExercise(exerciseId)
    }

    override fun inflateView(inflater: LayoutInflater) = DialogItemDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun setUI() {
        binding.apply {
            tvTitle.text = getString(R.string.exercise_add_delete_title)
            tvAlert.visibility = View.GONE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnDelete.setOnClickListener { viewModel.deleteExercise(exerciseId) }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DeleteCreateExerciseDialogEvent.CreatedExercise -> {
                    binding.tvText.text = getString(
                        R.string.exercise_add_delete_text,
                        event.exercise.name
                    )
                }

                is DeleteCreateExerciseDialogEvent.Deleted -> {
                    deleted()
                    dismiss()
                }

                is DeleteCreateExerciseDialogEvent.SWW -> {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.sww),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }

                else -> {}
            }
        }
    }
}