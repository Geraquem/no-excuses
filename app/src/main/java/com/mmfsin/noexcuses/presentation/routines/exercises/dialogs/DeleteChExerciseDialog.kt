package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogChExerciseDeleteBinding
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDayExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteChExerciseDialog(
    private val chExerciseId: String, private val listener: IDayExerciseListener
) : BaseDialog<DialogChExerciseDeleteBinding>() {

    private val viewModel: ChExerciseDialogViewModel by viewModels()

    private var exerciseName: String? = null
    private var dayId: String? = null
    private var dayName: String? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogChExerciseDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getChExercise(chExerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            if (exerciseName != null && dayName != null) {
                tvText.text =
                    getString(R.string.days_exercise_dialog_delete_text, exerciseName, dayName)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener { viewModel.deleteChExercise(chExerciseId) }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExerciseDialogEvent.GetExercise -> {
                    exerciseName = event.exercise.name
                    dayId?.let { id -> viewModel.getDay(id) }
                }
                is ChExerciseDialogEvent.GetDay -> {
                    dayName = event.day.title
                    setUI()
                }
                is ChExerciseDialogEvent.GetChExercise -> {
                    dayId = event.chExercise.dayId
                    val exerciseId = event.chExercise.exerciseId
                    exerciseId?.let { id -> viewModel.getExercise(id) }
                }
                is ChExerciseDialogEvent.FlowCompleted -> endFlow()
                is ChExerciseDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun endFlow() {
        listener.updateView()
        dismiss()
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            chExerciseId: String, listener: IDayExerciseListener
        ): DeleteChExerciseDialog {
            return DeleteChExerciseDialog(chExerciseId, listener)
        }
    }
}