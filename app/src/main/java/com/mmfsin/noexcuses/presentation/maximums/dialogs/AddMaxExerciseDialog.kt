package com.mmfsin.noexcuses.presentation.maximums.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogAddMaxExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.maximums.listeners.IAddMaxExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMaxExerciseDialog(
    private val exerciseId: String,
    private val listener: IAddMaxExerciseListener
) : BaseDialog<DialogAddMaxExerciseBinding>() {

    private val viewModel: AddMaxExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogAddMaxExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getExercise(exerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.gifURL).into(image)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            tvSeeExercise.setOnClickListener {
                exercise?.let { e -> listener.onSeeExerciseClick(e.id) }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is AddMaxExercisesDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is AddMaxExercisesDialogEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            chExerciseId: String,
            listener: IAddMaxExerciseListener
        ): AddMaxExerciseDialog {
            return AddMaxExerciseDialog(chExerciseId, listener)
        }
    }
}