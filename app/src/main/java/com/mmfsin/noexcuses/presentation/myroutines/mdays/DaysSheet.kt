package com.mmfsin.noexcuses.presentation.myroutines.mdays

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.mdays.adapter.DaysAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs.DayAddDialog
import com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs.DayDeleteDialog
import com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs.DayEditDialog
import com.mmfsin.noexcuses.presentation.myroutines.mdays.interfaces.IDaysListener
import com.mmfsin.noexcuses.presentation.myroutines.myroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysSheet(
    val routineId: String,
    val listener: IMyRoutineListener
) : BaseBottomSheet<DialogDaysBinding>(), IDaysListener {

    private val viewModel: DaysDialogViewModel by viewModels()

    private var mDays = emptyList<Day>()

    override fun inflateView(inflater: LayoutInflater) = DialogDaysBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 0.8).toInt()
            it.layoutParams = layoutParams
            behavior.peekHeight = layoutParams.height
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        observe()
        setListeners()

        viewModel.getRoutine(routineId)
    }

    override fun setListeners() {
        binding.apply {
            btnAddDay.setOnClickListener {
                childFragmentManager.showFragmentDialog(
                    DayAddDialog.newInstance(routineId, this@DaysSheet)
                )
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DaysDialogEvent.GetRoutine -> {
                    binding.tvRoutineName.text = event.routine.name
                    viewModel.getDays(routineId)
                }

                is DaysDialogEvent.GetDays -> setUpDays(event.days)
                is DaysDialogEvent.SWW -> error()
            }
        }
    }

    private fun setUpDays(days: List<Day>) {
        mDays = days
        binding.apply {
            rvDays.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = DaysAdapter(mDays, this@DaysSheet)
            }
            rvDays.isVisible = days.isNotEmpty()
            tvEmpty.isVisible = days.isEmpty()
        }
    }

    override fun flowCompleted() {
        mDays = emptyList()
        viewModel.getDays(routineId)
        listener.dayAddedToRoutine()
    }

    override fun onDayClick(id: String) {
        listener.onDayClick(routineId, dayId = id)
        dismiss()
    }

    override fun onDayLongClick(id: String) {
        childFragmentManager.showFragmentDialog(
            DayEditDialog.newInstance(id, this@DaysSheet)
        )
    }

    override fun openDeleteDayDialog(id: String) {
        childFragmentManager.showFragmentDialog(
            DayDeleteDialog.newInstance(id, this@DaysSheet)
        )
    }

    private fun error() = activity?.showErrorDialog()
}