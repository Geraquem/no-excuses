package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.base.swipelistener.OnSwipeListener
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.adapter.DefaultDaysAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.interfaces.IDefaultDaysListener
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultDaysDialog(
    val routineId: String,
    val listener: IDefaultRoutineListener
) : BaseDialog<DialogDaysBinding>(), IDefaultDaysListener {

    private val viewModel: DefaultDaysDialogViewModel by viewModels()

    private var mDays = emptyList<Day>()

    override fun inflateView(inflater: LayoutInflater) = DialogDaysBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = bottomCustomViewDialog(dialog, 0.95)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDefaultRoutine(routineId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            flBtnSeparator.visibility = View.GONE
            btnAddDay.visibility = View.GONE
        }
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
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DefaultDaysDialogEvent.GetDefaultRoutine -> {
                    binding.tvRoutineName.text = event.routine.title
                    viewModel.getDefaultDays(routineId)
                }

                is DefaultDaysDialogEvent.GetDefaultDays -> setUpDays(event.days)
                is DefaultDaysDialogEvent.SWW -> error()
            }
        }
    }

    private fun setUpDays(days: List<Day>) {
        mDays = days
        binding.apply {
            rvDays.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = DefaultDaysAdapter(mDays, this@DefaultDaysDialog)
            }
            rvDays.isVisible = days.isNotEmpty()
            tvEmpty.isVisible = days.isEmpty()
        }
    }

    override fun onDayClick(id: String) {
        listener.onDayClick(id)
        dismiss()
    }

    private fun error() = activity?.showErrorDialog()
}