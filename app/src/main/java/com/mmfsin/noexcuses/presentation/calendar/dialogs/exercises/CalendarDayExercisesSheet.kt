package com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogCalendarDayExercisesBinding
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarDayExercisesSheet(
    val dayId: String,
    val routineId: String,
) : BaseBottomSheet<DialogCalendarDayExercisesBinding>() {

    private val viewModel: CalendarDayExercisesViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) =
        DialogCalendarDayExercisesBinding.inflate(inflater)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)

                val metrics = Resources.getSystem().displayMetrics
                val maxHeight = (metrics.heightPixels * 0.9).toInt()
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()

                it.background = getDrawable(requireContext(), R.drawable.bg_header_dialog)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        observe()

        viewModel.getExercises(dayId, routineId)
    }

    override fun setUI() {
        isCancelable = true
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CalendarDayExercisesEvent.GetExercises -> setData(event.exercises)
                is CalendarDayExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setData(days: String) {
        binding.apply {
//            rvExercises.apply {
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = DefaultDaysAdapter(mDays, this@CalendarDayExercisesSheet)
//            }
//            tvEmpty.isVisible = days.isEmpty()
        }
    }

    private fun error() = activity?.showErrorDialog()
}