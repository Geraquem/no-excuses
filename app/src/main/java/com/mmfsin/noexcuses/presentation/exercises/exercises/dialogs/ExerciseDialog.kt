package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.DialogExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.utils.loadNativeAds
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

                if (it.gifURL.isNullOrEmpty()) {
                    gifImage.isVisible = false
                } else Glide.with(requireContext()).load(it.gifURL).into(gifImage)

                if (it.muscleWikiURL.isNullOrEmpty()) {
                    llMuscleWiki.isVisible = false
                } else {
                    if (it.createdByUser) {
                        ivMw.setImageResource(R.drawable.ic_search)
                        tvMw.text = getString(R.string.exercise_dialog_search)
                    }
                }

                if (it.description.isEmpty()) {
                    tvDescriptionTitle.isVisible = false
                    tvDescription.isVisible = false
                } else tvDescription.text = it.description

                if (it.involvedMuscles.isEmpty()) {
                    tvMusclesTitle.isVisible = false
                    tvMuscles.isVisible = false
                } else tvMuscles.text = it.involvedMuscles

                updateFavIcon(it.isFav)
            }

            context?.loadNativeAds(binding.nativeAd, binding.nativeContent)
        }
    }

    fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            ivFav.setOnClickListener { exercise?.let { e -> viewModel.updateFav(e.id) } }

            llMuscleWiki.setOnClickListener {
                try {
                    exercise?.let { e ->
                        e.muscleWikiURL?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exercise_dialog_url_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
//        val favText = if (isFav) R.string.favs_delete else R.string.favs_save
        binding.ivFav.setImageResource(icon)
    }

    private fun error() = activity?.showErrorDialog()
}