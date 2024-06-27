package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.DialogExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDialog(private val exerciseId: String) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogExerciseBinding

    private val viewModel: ExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
        viewModel.getExercise(exerciseId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)

                val metrics = Resources.getSystem().displayMetrics
                val maxHeight = (metrics.heightPixels * 1)
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExercise(exerciseId)
        observe()
        setListeners()
    }

    fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.imageURL).into(image)
                tvDescription.text = it.description
                tvMuscles.text = it.involvedMuscles
                updateFavIcon(it.isFav)
            }
        }
    }

    fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            llFav.setOnClickListener { exercise?.let { e -> viewModel.updateFav(e.id) } }
            llVideo.setOnClickListener { }
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
        val favText = if (isFav) R.string.favs_delete else R.string.favs_save
        binding.apply {
            ivFav.setImageResource(icon)
            tvFav.text = getString(favText)
        }
    }

    private fun error() = activity?.showErrorDialog()
}