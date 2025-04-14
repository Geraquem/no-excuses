package com.mmfsin.noexcuses.presentation.maximums.dialogs.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogAddMaxExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DatePickerDialog
import com.mmfsin.noexcuses.presentation.maximums.listeners.IDialogsMaxExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.getMonthName
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMaxExerciseDialog(
    private val exerciseId: String,
    private val listener: IDialogsMaxExerciseListener
) : BaseDialog<DialogAddMaxExerciseBinding>() {

    private val viewModel: AddMaxExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null
    private var selectedDate: String? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogAddMaxExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getExercise(exerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            tvError.isVisible = false
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.gifURL).into(image)
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            tvSeeExercise.setOnClickListener {
                exercise?.let { e -> listener.onSeeExerciseClick(e.id) }
            }

            btnOpenCalendar.setOnClickListener {
                activity?.let {
                    val calendar = DatePickerDialog { d, m, y ->
                        selectedDate = "$d/$m/$y"
                        tvSelectedDate.text = getString(
                            R.string.maximums_selected_date_str,
                            d.toString(), m.getMonthName(), y.toString()
                        )
                    }
                    calendar.show(it.supportFragmentManager, "")
                }
            }

            btnAdd.setOnClickListener {
                selectedDate?.let { date ->
                    val weight = etWeight.text.toString()
                    if (weight.isNotEmpty()) {
                        try {
                            val doubleWeight = weight.toDouble()
                            val maxData = TempMaximumData(exerciseId, doubleWeight, date)
                            viewModel.saveMaximumData(maxData)
                            tvError.isVisible = false
                        } catch (e: Exception) {
                            tvError.isVisible = true
                        }
                    } else tvError.isVisible = true
                } ?: run { tvError.isVisible = true }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is AddMaxExercisesDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is AddMaxExercisesDialogEvent.Registered -> {
                    Toast.makeText(requireContext(), R.string.maximums_added, Toast.LENGTH_SHORT)
                        .show()
                    dismiss()
                }

                is AddMaxExercisesDialogEvent.SWW -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            chExerciseId: String,
            listener: IDialogsMaxExerciseListener
        ): AddMaxExerciseDialog {
            return AddMaxExerciseDialog(chExerciseId, listener)
        }
    }
}