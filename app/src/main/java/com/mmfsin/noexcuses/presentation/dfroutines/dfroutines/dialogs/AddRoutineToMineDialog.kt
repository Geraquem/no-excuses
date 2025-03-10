package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogAddRoutineBinding
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.countDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoutineToMineDialog(
    private val routineId: String,
    private val routineName: String,
) : BaseDialog<DialogAddRoutineBinding>() {

    private val viewModel: AddRoutineToMineViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater) =
        DialogAddRoutineBinding.inflate(inflater)

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
            loading.isVisible = false
            llAdded.isVisible = false
            tvText.text = getString(R.string.df_routines_add_description, routineName)
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAdd.setOnClickListener {
                isCancelable = false
                binding.apply {
                    loading.isVisible = true
                    llMain.visibility = View.INVISIBLE
                }
                viewModel.addRoutineToMine(routineId)
            }
            btnCancel.setOnClickListener { dismiss() }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is AddRoutineToMineEvent.RoutineAddedToMine -> {
                    binding.apply {
                        loading.isVisible = false
                        llAdded.isVisible = true
                    }
                    countDown(1000) { dismiss() }
                }

                is AddRoutineToMineEvent.SWW -> {
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
        fun newInstance(routineId: String, routineName: String): AddRoutineToMineDialog {
            return AddRoutineToMineDialog(routineId, routineName)
        }
    }
}