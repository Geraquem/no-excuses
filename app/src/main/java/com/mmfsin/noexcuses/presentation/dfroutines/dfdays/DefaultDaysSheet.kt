package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

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
import com.mmfsin.noexcuses.databinding.DialogDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.adapter.DefaultDaysAdapter
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.interfaces.IDefaultDaysListener
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DefaultDaysSheet(
    val routineId: String,
    val listener: IDefaultRoutineListener
) : BottomSheetDialogFragment(), IDefaultDaysListener {

    private val viewModel: DefaultDaysDialogViewModel by viewModels()

    private lateinit var binding: DialogDaysBinding

    private var mDays = emptyList<Day>()

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

                it.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_header_dialog)
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        observe()

        viewModel.getDefaultRoutine(routineId)
    }

    private fun setUI() {
        isCancelable = true
        binding.apply {
            flBtnSeparator.visibility = View.GONE
            btnAddDay.visibility = View.GONE
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
                adapter = DefaultDaysAdapter(mDays, this@DefaultDaysSheet)
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