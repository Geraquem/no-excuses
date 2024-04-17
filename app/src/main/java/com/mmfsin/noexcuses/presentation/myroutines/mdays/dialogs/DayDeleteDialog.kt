package com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemDeleteBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.mdays.interfaces.IDaysListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayDeleteDialog(private val dayId: String, private val listener: IDaysListener) :
    BaseDialog<DialogItemDeleteBinding>() {

    private val viewModel: DayConfigViewModel by viewModels()

    private var day: Day? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogItemDeleteBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDay(dayId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvTitle.text = getString(R.string.days_delete_top_text)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnNo.setOnClickListener { dismiss() }
            btnYes.setOnClickListener { day?.let { viewModel.deleteDay(it.id) } }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DayConfigEvent.FlowCompleted -> {
                    listener.flowCompleted()
                    dismiss()
                }

                is DayConfigEvent.GetDay -> {
                    day = event.day
                    binding.tvText.text = getString(R.string.routines_delete_text, day?.title)
                }

                is DayConfigEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(id: String, listener: IDaysListener): DayDeleteDialog {
            return DayDeleteDialog(id, listener)
        }
    }
}