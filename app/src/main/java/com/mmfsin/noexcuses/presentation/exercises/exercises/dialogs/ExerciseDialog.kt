package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDialog(private val exerciseId: String) : BaseDialog<DialogExerciseBinding>() {

    private val viewModel: ExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    override fun inflateView(inflater: LayoutInflater) = DialogExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomViewDialog(dialog)

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
                Glide.with(requireContext()).load(it.imageURL).into(image)
                tvDescription.text = it.description
                tvMuscles.text = it.involvedMuscles

                val favIcon = if (it.isFav) R.drawable.ic_fav_on else R.drawable.ic_fav_off
                ivFav.setImageResource(favIcon)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            ivFav.setOnClickListener { exercise?.let { e -> viewModel.updateFav(e.id) } }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ExerciseDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is ExerciseDialogEvent.CheckFav -> updateFavIcon(event.isFav)
                is ExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun updateFavIcon(isFav: Boolean) {
        val icon = if (isFav) R.drawable.ic_fav_on else R.drawable.ic_fav_off
        binding.ivFav.setImageResource(icon)
    }

    private fun error() = activity?.showErrorDialog()
}