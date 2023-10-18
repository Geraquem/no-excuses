package com.mmfsin.noexcuses.presentation.myroutines.days

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.days.adapter.DaysAdapter
import com.mmfsin.noexcuses.presentation.myroutines.days.fragments.AddDaysConfigDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.fragments.DeleteDayDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.fragments.EditDaysConfigDialog
import com.mmfsin.noexcuses.presentation.myroutines.days.interfaces.IDayListener
import com.mmfsin.noexcuses.presentation.myroutines.days.interfaces.IDaysDialogListener
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysDialog(
    val routineId: String,
    val listener: IMyRoutineListener,
) : BaseDialog<DialogDaysBinding>(), IDayListener, IDaysDialogListener {

    private val viewModel: DaysViewModel by viewModels()

    private var days = emptyList<Day>()

    override fun inflateView(inflater: LayoutInflater) = DialogDaysBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomCustomViewDialog(dialog, 0.9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDays(routineId)
    }

    override fun setUI() {
        isCancelable = true
    }

    override fun setListeners() {
        binding.btnAddDay.setOnClickListener {
            childFragmentManager.showFragmentDialog(
                AddDaysConfigDialog.newInstance(routineId, this@DaysDialog)
            )
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DaysEvent.GetDays -> {
                    days = event.days
                    setUpDays(days)
                }
                is DaysEvent.GetDay -> {}
                is DaysEvent.GetDayExercises -> {}
                is DaysEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDays(days: List<Day>) {
        binding.apply {
            rvDays.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = DaysAdapter(days, this@DaysDialog)
            }
            rvDays.isVisible = days.isNotEmpty()
            tvEmpty.isVisible = days.isEmpty()
        }
    }

    override fun onDayClick(id: String) {
        listener.onDayClick(routineId, dayId = id)
        dismiss()
    }

    override fun onDayLongClick(id: String) {
        childFragmentManager.showFragmentDialog(
            EditDaysConfigDialog.newInstance(id, this@DaysDialog)
        )
    }

    override fun flowCompleted() {
        days = emptyList()
        viewModel.getDays(routineId)
        listener.dayAddedToRoutine()
    }

    override fun deleteDay(id: String) {
        childFragmentManager.showFragmentDialog(
            DeleteDayDialog.newInstance(id, this@DaysDialog)
        )
    }

    private fun error() = activity?.showErrorDialog()
}