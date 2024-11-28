package com.mmfsin.noexcuses.presentation.myroutines.dialogs

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogInfoBinding
import com.mmfsin.noexcuses.domain.models.getCategoryColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDialog : BaseBottomSheet<DialogInfoBinding>() {

    override fun inflateView(inflater: LayoutInflater) = DialogInfoBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 0.95).toInt()
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            routine.apply {
                image.tvNumOfDays.text = getString(R.string.first_dialog_routine_days)
                tvTitle.text = getString(R.string.first_dialog_routine_title)
                tvDescription.text = getString(R.string.first_dialog_routine_description)
            }

            day.apply {
                image.firstLetter.text = getString(R.string.first_dialog_day_letter)
                tvTitle.text = getString(R.string.first_dialog_day_title)
                tvExercises.text = getString(R.string.days_exercises, "6")
            }

            val c = requireActivity().applicationContext
            val category = getString(R.string.first_dialog_exercise_category)
            exercise.apply {
                Glide.with(c).load(R.drawable.press).into(image)
                tvCategory.text = category
                tvCategory.background.setTint(getColor(c, getCategoryColor(category)))
                tvName.text = getString(R.string.first_dialog_exercise_name)
                tvSeries.text = getString(R.string.first_dialog_exercise_series)
                tvWait.text = getString(R.string.first_dialog_exercise_rest)
                ivHasNotes.isVisible = false
                llAddData.isVisible = false
                ivEdit.isVisible = false
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            ivClose.setOnClickListener { dismiss() }
            btnAccept.setOnClickListener { dismiss() }
        }
    }
}