package com.mmfsin.noexcuses.presentation.myroutines.days.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemEditBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.days.interfaces.IDaysListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.closeKeyboard
import com.mmfsin.noexcuses.utils.countDown300
import com.mmfsin.noexcuses.utils.isKeyboardVisible
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayEditDialog(
    private val dayId: String,
    private val listener: IDaysListener
) : BaseDialog<DialogItemEditBinding>() {

    private val viewModel: DayConfigViewModel by viewModels()

    private var day: Day? = null

    override fun inflateView(inflater: LayoutInflater) = DialogItemEditBinding.inflate(inflater)

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
            tvTitle.text = getString(R.string.days_edit_top_text)
            llDescription.visibility = View.GONE
            tvError.visibility = View.GONE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnDelete.setOnClickListener {
                day?.let {
                    listener.openDeleteDayDialog(it.id)
                    dismiss()
                }
            }

            btnEdit.setOnClickListener {
                day?.let { d ->
                    val title = etTitle.text.toString()
                    if (title.isNotEmpty() && title.isNotBlank()) {
                        if (isKeyboardVisible(btnEdit)) closeKeyboard()
                        countDown300 { viewModel.editDay(d.id, title) }
                    } else binding.tvError.visibility = View.VISIBLE
                }
            }
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
                    binding.etTitle.setText(day?.title)
                }

                is DayConfigEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(routineId: String, listener: IDaysListener): DayEditDialog {
            return DayEditDialog(routineId, listener)
        }
    }
}