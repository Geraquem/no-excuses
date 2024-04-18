package com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogItemAddBinding
import com.mmfsin.noexcuses.presentation.myroutines.mdays.interfaces.IDaysListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.closeKeyboard
import com.mmfsin.noexcuses.utils.countDown300
import com.mmfsin.noexcuses.utils.isKeyboardVisible
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayAddDialog(
    private val routineId: String,
    private val listener: IDaysListener
) : BaseDialog<DialogItemAddBinding>() {

    private val viewModel: DayConfigViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) = DialogItemAddBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvTitle.text = getString(R.string.days_add_top_text)
            llDescription.visibility = View.GONE
            tvError.visibility = View.GONE
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                val title = etTitle.text.toString()
                if (title.isNotEmpty() && title.isNotBlank()) {
                    if (isKeyboardVisible(btnAccept)) closeKeyboard()
                    countDown300 { viewModel.addDay(routineId, title) }
                } else tvError.visibility = View.VISIBLE
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

                is DayConfigEvent.GetDay -> {}
                is DayConfigEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()


    companion object {
        fun newInstance(routineId: String, listener: IDaysListener): DayAddDialog {
            return DayAddDialog(routineId, listener)
        }
    }
}