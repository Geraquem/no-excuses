package com.mmfsin.noexcuses.presentation.maximums.dialogs.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogEditMaxExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DatePickerDialog
import com.mmfsin.noexcuses.presentation.maximums.listeners.IDialogsMaxExerciseListener
import com.mmfsin.noexcuses.presentation.maximums.trigger.TriggerManager
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.deletePointZero
import com.mmfsin.noexcuses.utils.getMonthName
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.toCompleteDate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditMDataDialog(
    private val exerciseId: String,
    private val mDataId: String,
    private val listener: IDialogsMaxExerciseListener
) : BaseDialog<DialogEditMaxExerciseBinding>() {

    @Inject
    lateinit var trigger: TriggerManager

    private val viewModel: EditMDataDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    private var selectedDate: String? = null
    private var weight: Double? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogEditMaxExerciseBinding.inflate(inflater)

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

            btnDelete.setOnClickListener { listener.deleteMData(mDataId) }

            btnEdit.setOnClickListener {
                selectedDate?.let { date ->
                    val weight = etWeight.text.toString()
                    if (weight.isNotEmpty()) {
                        try {
                            val doubleWeight = weight.toDouble()
                            val maxData = TempMaximumData(exerciseId, doubleWeight, date)
                            viewModel.editMData(mDataId, maxData)

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
                is EditMDataDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                    viewModel.getMData(mDataId)
                }

                is EditMDataDialogEvent.GetMData -> setMData(event.data)
                is EditMDataDialogEvent.Edited -> {
                    trigger.updateTrigger()
                    dismiss()
                }

                is EditMDataDialogEvent.SWW -> error()
            }
        }
    }

    private fun setMData(data: MData) {
        binding.apply {
            selectedDate = data.date
            weight = data.weight

            tvSelectedDate.text = data.date.toCompleteDate(requireContext())
            etWeight.setText(weight.deletePointZero())
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            exerciseId: String,
            mDataId: String,
            listener: IDialogsMaxExerciseListener
        ): EditMDataDialog {
            return EditMDataDialog(exerciseId, mDataId, listener)
        }
    }
}