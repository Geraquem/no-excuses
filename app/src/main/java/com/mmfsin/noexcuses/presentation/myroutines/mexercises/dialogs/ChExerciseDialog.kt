package com.mmfsin.noexcuses.presentation.myroutines.mexercises.dialogs

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
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.ChExerciseDialogEvent
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.ChExerciseDialogViewModel
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.adapter.ChExerciseAdapter
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.formatTime
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChExerciseDialog(
    private val chExerciseId: String,
    private val listener: IMExerciseListener
) : BaseDialog<DialogChExerciseBinding>() {

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
            rvHeader.ivDelete.visibility = View.GONE
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
                exercise?.let { e ->
                    listener.onSeeExerciseButtonClick(e.id)
                }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExerciseDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                }

                is ChExerciseDialogEvent.GetDay -> {}
                is ChExerciseDialogEvent.GetChExercise -> setData(event.chExercise)
                is ChExerciseDialogEvent.FlowCompleted -> {}
                is ChExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun setData(chExercise: ChExercise) {
        binding.apply {
            chExercise.data?.let { data -> setUpSeriesRV(data) }
            chExercise.time?.let { time -> tvTime.text = time.formatTime() }
                ?: run { llTime.visibility = View.GONE }
            tvSuperSerie.isVisible = chExercise.superSerie
            chExercise.notes?.let { notes -> tvNotes.text = notes } ?: run {
                tvNotes.visibility = View.GONE
            }
            chExercise.exerciseId?.let { id -> viewModel.getExercise(id) } ?: run { error() }
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
        fun newInstance(chExerciseId: String, listener: IMExerciseListener): ChExerciseDialog {
            return ChExerciseDialog(chExerciseId, listener)
        }
    }
}