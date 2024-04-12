package com.mmfsin.noexcuses.presentation.myroutines.days

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.base.swipelistener.OnSwipeListener
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.days.adapter.DaysAdapter
import com.mmfsin.noexcuses.presentation.myroutines.days.dialogs.DayAddDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.dialogs.DayDeleteDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.dialogs.DayEditDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.interfaces.IDaysListener
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysDialog(
    val routineId: String,
    val listener: IMyRoutineListener
) : BaseDialog<DialogDaysBinding>(), IDaysListener {

    private val viewModel: DaysDialogViewModel by viewModels()

    private var mDays = emptyList<Day>()

    override fun inflateView(inflater: LayoutInflater) = DialogDaysBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomCustomViewDialog(dialog, 0.95)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getRoutine(routineId)
    }

    override fun setUI() {
        isCancelable = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {
        binding.apply {
            ivDismiss.setOnClickListener { dismiss() }

            rvDays.setOnTouchListener(object : OnSwipeListener(requireContext()) {
                override fun onSwipeBottom() {
                    dismiss()
                }
            })

            btnAddDay.setOnClickListener {
                childFragmentManager.showFragmentDialog(
                    DayAddDialog.newInstance(routineId, this@DaysDialog)
                )
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DaysDialogEvent.GetRoutine -> {
                    binding.tvRoutineName.text = event.routine.title
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
                adapter = DaysAdapter(mDays, this@DaysDialog)
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
            DayEditDialog.newInstance(id, this@DaysDialog)
        )
    }

    override fun openDeleteDayDialog(id: String) {
        childFragmentManager.showFragmentDialog(
            DayDeleteDialog.newInstance(id, this@DaysDialog)
        )
    }

    private fun error() = activity?.showErrorDialog()
}