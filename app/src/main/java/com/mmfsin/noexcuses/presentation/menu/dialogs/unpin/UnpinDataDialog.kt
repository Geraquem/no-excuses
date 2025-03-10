package com.mmfsin.noexcuses.presentation.menu.dialogs.unpin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogUnpinDataBinding
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_NOTE
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_ROUTINE
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.updateMenuUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnpinDataDialog(
    private val dataId: String,
    private val unpinType: UnpinType
) : BaseDialog<DialogUnpinDataBinding>() {

    private val viewModel: UnpinDataViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) =
        DialogUnpinDataBinding.inflate(inflater)

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
            when (unpinType) {
                UNPIN_ROUTINE -> {
                    tvTitle.text = getString(R.string.menu_unpin_routine_title)
                    tvText.text = getString(R.string.menu_unpin_routine)
                    tvAlert.text = getString(R.string.menu_unpin_routine_description)
                }

                UNPIN_NOTE -> {
                    tvTitle.text = getString(R.string.menu_unpin_note_title)
                    tvText.text = getString(R.string.menu_unpin_note)
                    tvAlert.text = getString(R.string.menu_unpin_note_description)

                }
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnUnpin.setOnClickListener {
                when (unpinType) {
                    UNPIN_ROUTINE -> viewModel.unpinData(dataId, UNPIN_ROUTINE)
                    UNPIN_NOTE -> viewModel.unpinData(dataId, UNPIN_NOTE)
                }
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is UnpinDataEvent.Unpinned -> {
                    activity?.updateMenuUI(requireActivity())
                    dismiss()
                }

                is UnpinDataEvent.SWW -> {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.sww),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        }
    }

    companion object {
        enum class UnpinType {
            UNPIN_ROUTINE,
            UNPIN_NOTE,
        }

        fun newInstance(dataId: String, unpinType: UnpinType): UnpinDataDialog {
            return UnpinDataDialog(dataId, unpinType)
        }
    }
}