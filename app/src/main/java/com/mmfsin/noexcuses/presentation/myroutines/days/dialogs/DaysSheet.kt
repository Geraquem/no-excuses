package com.mmfsin.noexcuses.presentation.myroutines.days.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.days.DaysEvent
import com.mmfsin.noexcuses.presentation.myroutines.days.DaysViewModel
import com.mmfsin.noexcuses.presentation.myroutines.routines.interfaces.IMyRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysSheet(
    val routineId: String,
    val listener: IMyRoutineListener,
) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogDaysBinding

    private val viewModel: DaysViewModel by viewModels()

    private var days = emptyList<Day>()

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
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                val behaviour = BottomSheetBehavior.from(parent)
                setupFullHeight(parent)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDays(routineId)
        observe()
        setListeners()
    }

    private fun setListeners() {
        binding.btnAddDay.setOnClickListener {
//            childFragmentManager.showFragmentDialog(
//                AddDaysConfigDialog.newInstance(routineId, this@DaysSheet)
//            )
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
//            rvDays.apply {
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = DaysAdapter(days, this@DaysSheet)
//            }
            rvDays.isVisible = days.isNotEmpty()
            tvEmpty.isVisible = days.isEmpty()
        }
    }

//    override fun onDayClick(id: String) {
//        listener.onDayClick(routineId, dayId = id)
//        dismiss()
//    }
//
//    override fun onDayLongClick(id: String) {
//        childFragmentManager.showFragmentDialog(
//            EditDaysConfigDialog.newInstance(id, this@DaysSheet)
//        )
//    }
//
//    override fun flowCompleted() {
//        days = emptyList()
//        viewModel.getDays(routineId)
//        listener.dayAddedToRoutine()
//    }

//    override fun deleteDay(id: String) {
//        childFragmentManager.showFragmentDialog(
//            DeleteDayDialog.newInstance(id, this@DaysSheet)
//        )
//    }

    private fun error() = activity?.showErrorDialog()
}