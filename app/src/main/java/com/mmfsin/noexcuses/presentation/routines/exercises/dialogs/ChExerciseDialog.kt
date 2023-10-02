package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogChExerciseBinding
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.adapter.ChExerciseAdapter
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChExerciseDialog(private val chExerciseId: String) : BaseDialog<DialogChExerciseBinding>() {

    private val viewModel: ChExerciseDialogViewModel by viewModels()

    private var exercise: Exercise? = null

    override fun inflateView(inflater: LayoutInflater) = DialogChExerciseBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        viewModel.getChExercise(chExerciseId)
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.imageURL).into(image)
            }
        }
    }

    override fun setListeners() {
        binding.apply { }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExerciseDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is ChExerciseDialogEvent.GetChExercise -> setData(event.chExercise)
                is ChExerciseDialogEvent.GetDay -> {}
                is ChExerciseDialogEvent.AddedCompleted -> {}
                is ChExerciseDialogEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setData(chExercise: ChExercise) {
        binding.apply {
            chExercise.data?.let { data -> setUpSeriesRV(data) }
            chExercise.time?.let { time -> binding.tvTime.text = time.toString() }
                ?: run { llTime.visibility = View.GONE }
            chExercise.exerciseId?.let { id -> viewModel.getExercise(id) }
                ?: run { error() }
        }
    }

    private fun setUpSeriesRV(series: List<Data>) {
        binding.apply {
            rvSeries.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ChExerciseAdapter(series)
            }
            rvHeader.root.isVisible = series.isNotEmpty()
            rvSeries.isVisible = series.isNotEmpty()
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(chExerciseId: String): ChExerciseDialog {
            return ChExerciseDialog(chExerciseId)
        }
    }
}