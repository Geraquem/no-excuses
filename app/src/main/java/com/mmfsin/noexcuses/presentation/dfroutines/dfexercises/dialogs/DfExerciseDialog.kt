package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogDefaultExerciseBinding
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter.DfExerciseSeriesAdapter
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DfExerciseDialog(private val dfExerciseId: String) :
    BaseDialog<DialogDefaultExerciseBinding>() {

    private val viewModel: DfExerciseDialogViewModel by viewModels()

    private var exercise: DefaultExercise? = null

    override fun inflateView(inflater: LayoutInflater) =
        DialogDefaultExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getDefaultExercise(dfExerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.exercise.category)
                tvName.text = it.exercise.name
                Glide.with(requireContext()).load(it.exercise.imageURL).into(image)
                tvTime.text = it.desc
                setUpSeriesRV(it.reps.split(","))
            }
        }
    }

    override fun setListeners() {}

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DfExerciseDialogEvent.GetDefaultExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is DfExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun setUpSeriesRV(series: List<String>) {
        binding.apply {
            rvSeries.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = DfExerciseSeriesAdapter(series)
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(chExerciseId: String): DfExerciseDialog {
            return DfExerciseDialog(chExerciseId)
        }
    }
}