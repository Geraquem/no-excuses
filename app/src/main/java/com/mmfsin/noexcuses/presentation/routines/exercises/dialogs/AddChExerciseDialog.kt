package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogChExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddChExerciseDialog(private val idGroup: IdGroup) : BaseDialog<DialogChExerciseBinding>() {

    private val viewModel: ChExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    override fun inflateView(inflater: LayoutInflater) = DialogChExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        idGroup.exerciseId?.let { id -> viewModel.getExercise(id) } ?: run { error() }
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.imageURL).into(image)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAdd.setOnClickListener {
                /** get info from screen */
                val data = DataChExercise(/** bla bla bla bla */)
                viewModel.addChExercise(idGroup, data)
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExerciseDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                    viewModel.getDay(idGroup.dayId)
                }
                is ChExerciseDialogEvent.GetDay -> binding.btnAdd.text =
                    getString(R.string.ch_exercise_dialog_add, event.day.title)

                is ChExerciseDialogEvent.AddedCompleted -> {

                }

                is ChExerciseDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()


    companion object {
        fun newInstance(idGroup: IdGroup): AddChExerciseDialog {
            return AddChExerciseDialog(idGroup)
        }
    }
}