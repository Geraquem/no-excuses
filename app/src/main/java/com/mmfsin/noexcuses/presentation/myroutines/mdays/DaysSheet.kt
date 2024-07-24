package com.mmfsin.noexcuses.presentation.myroutines.mdays

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.swipelistener.OnSwipeListener
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
) : BottomSheetDialogFragment(), IDaysListener {

    private val viewModel: DaysDialogViewModel by viewModels()

    private var mDays = emptyList<Day>()

    private lateinit var binding: DialogDaysBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeNoFloating)
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
                val maxHeight = (metrics.heightPixels * 0.95).toInt()
                it.layoutParams.height = maxHeight
                behavior.peekHeight = maxHeight
                it.requestLayout()

                it.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_header_dialog)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        observe()
        setListeners()

        viewModel.getRoutine(routineId)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
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