package com.mmfsin.noexcuses.presentation.menu.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mmfsin.noexcuses.base.BaseBottomSheet
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.menu.dialogs.adapter.MenuDaysAdapter
import com.mmfsin.noexcuses.presentation.menu.dialogs.listener.IMenuDaysListener
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuDaysSheet(
    val routineId: String,
    val createdByUser: Boolean,
    val listener: IMenuListener
) : BaseBottomSheet<DialogDaysBinding>(), IMenuDaysListener {

    private val viewModel: MenuDaysDialogViewModel by viewModels()

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
        setUI()
        observe()

        viewModel.getPinnedRoutine()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            flBtnSeparator.visibility = View.GONE
            btnAddDay.visibility = View.GONE
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuDaysDialogEvent.GetRoutine -> {
                    binding.tvRoutineName.text = event.routine.name
                    viewModel.getPinnedRoutineDays(routineId)
                }

                is MenuDaysDialogEvent.GetDays -> setUpDays(event.days)
                is MenuDaysDialogEvent.SWW -> error()
            }
        }
    }

    private fun setUpDays(days: List<Day>) {
        binding.apply {
            rvDays.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = MenuDaysAdapter(days, createdByUser, this@MenuDaysSheet)
            }
            rvDays.isVisible = days.isNotEmpty()
            tvEmpty.isVisible = days.isEmpty()
            loading.isVisible = false
        }
    }

    override fun onDayClick(dayId: String) =
        listener.onMenuDayClick(routineId, dayId, createdByUser)

    private fun error() = activity?.showErrorDialog()
}