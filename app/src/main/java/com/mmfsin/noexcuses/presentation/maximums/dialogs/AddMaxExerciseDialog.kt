package com.mmfsin.noexcuses.presentation.maximums.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogAddMaxExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DatePickerDialog
import com.mmfsin.noexcuses.presentation.maximums.listeners.IAddMaxExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.deletePointZero
import com.mmfsin.noexcuses.utils.getMonthName
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneId.systemDefault
import java.util.Date

@AndroidEntryPoint
class AddMaxExerciseDialog(
    private val exerciseId: String,
    private val listener: IAddMaxExerciseListener
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
                        val newWeight = weight.toDouble().deletePointZero()
                        val maxData = TempMaximumData(exerciseId, newWeight, date)
                        viewModel.saveMaximumData(maxData)
                    }
                }
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
            listener: IAddMaxExerciseListener
        ): AddMaxExerciseDialog {
            return AddMaxExerciseDialog(chExerciseId, listener)
        }
    }
}