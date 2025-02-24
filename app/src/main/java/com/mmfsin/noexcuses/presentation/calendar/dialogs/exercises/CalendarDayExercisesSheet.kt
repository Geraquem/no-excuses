package com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogCalendarDayExercisesBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.calendar.adapter.CalendarDfExercisesAdapter
import com.mmfsin.noexcuses.presentation.calendar.adapter.CalendarMExercisesAdapter
import com.mmfsin.noexcuses.presentation.calendar.interfaces.ICalendarDayExercisesListener
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs.ChExerciseDialog
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarDayExercisesSheet(
    val dayId: String,
    val routineId: String,
) : BaseBottomSheet<DialogCalendarDayExercisesBinding>(),
    ICalendarDayExercisesListener, IMExerciseListener {

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
                is CalendarDayExercisesEvent.GetExercises -> setUpExercises(event.exercises)
                is CalendarDayExercisesEvent.SWW -> error()
            }
        }
    }

    private fun setUpExercises(exercises: List<Any>) {
        /** Get Adapter */
        val customAdapter = getAdapter(exercises)
        binding.apply {
            /** Setup Recycler */
            if (customAdapter is CalendarDfExercisesAdapter) {
                rvExercises.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = customAdapter
                }
            } else if (customAdapter is CalendarMExercisesAdapter) {
                rvExercises.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = customAdapter
                }
            }

            rvExercises.isVisible = exercises.isNotEmpty()
            tvEmpty.isVisible = exercises.isEmpty()
        }
    }

    private fun getAdapter(exercises: List<Any>): Any? {
        if (exercises.isNotEmpty()) {
            when (exercises.first()) {
                is DefaultExercise -> {
                    val dfExercises = exercises.mapNotNull { it as? DefaultExercise }
                    return CalendarDfExercisesAdapter(
                        dfExercises,
                        this@CalendarDayExercisesSheet
                    )
                }

                is CompactExercise -> {
                    val dfExercises = exercises.mapNotNull { it as? CompactExercise }
                    return CalendarMExercisesAdapter(
                        dfExercises.toMutableList(),
                        this@CalendarDayExercisesSheet
                    )
                }

                else -> return null
            }
        } else return null
    }

    override fun showExercise(exerciseId: String, createdByUser: Boolean) {
        if (createdByUser) {
            val dialogFragment = ChExerciseDialog(
                exerciseId,
                this@CalendarDayExercisesSheet
            )
            dialogFragment.show(childFragmentManager, "")

        } else activity?.showFragmentDialog(ExerciseDialog(exerciseId))
    }

    override fun onExerciseClick(chExerciseId: String) {}
    override fun editExercise(chExerciseId: String) {}
    override fun onSeeExerciseButtonClick(id: String) {}
    override fun deleteExerciseFromDay(chExerciseId: String) {}
    override fun updateView() {}

    private fun error() = activity?.showErrorDialog()
}