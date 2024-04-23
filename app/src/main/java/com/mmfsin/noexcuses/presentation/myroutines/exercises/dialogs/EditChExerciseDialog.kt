package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs

import android.animation.Animator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogChExerciseEditBinding
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.adapter.EditChExerciseAdapter
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.interfaces.IAddChExerciseListener
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.deletePointZero
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditChExerciseDialog(
    private val chExerciseId: String, private val listener: IMExerciseListener
) : BaseDialog<DialogChExerciseEditBinding>(), IAddChExerciseListener {

    private val viewModel: ChExerciseDialogViewModel by viewModels()

    private var mAdapter: EditChExerciseAdapter? = null

    private var exercise: Exercise? = null
    private var series = mutableListOf<Data>()
    private var seriesCont = 0

    override fun inflateView(inflater: LayoutInflater) =
        DialogChExerciseEditBinding.inflate(inflater)

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
        binding.apply {
            btnAddSerie.setOnClickListener { addSerie() }

            btnDelete.setOnClickListener {
                listener.deleteExerciseFromDay(chExerciseId)
                dismiss()
            }

            btnEdit.setOnClickListener {
                /** For Series */
                val mSeries = if (series.isEmpty()) null else series

                /** For Time */
                val time = etTime.text.toString()
                var restTime: Double? = null
                if (time.isNotEmpty()) {
                    restTime = try {
                        time.toDouble()
                    } catch (e: Exception) {
                        null
                    }
                }

                /** For Notes */
                val notes = etNotes.text.toString()
                val notesStr = notes.ifEmpty { null }

                val data = DataChExercise(dataList = mSeries, time = restTime, notes = notesStr)
                viewModel.editChExercise(chExerciseId, data)
            }
        }
    }

    private fun addSerie() {
        seriesCont++
        series.add(Data(id = seriesCont.toString()))
        mAdapter?.notifyItemInserted(series.size - 1)
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
                is ChExerciseDialogEvent.FlowCompleted -> endFlow()
                is ChExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun setData(chExercise: ChExercise) {
        binding.apply {
            chExercise.data?.let { data ->
                series = data.toMutableList()
                seriesCont = series.size
                setUpSeriesRV()
            }
            chExercise.time?.let { time -> etTime.setText(time.deletePointZero()) }
            chExercise.notes?.let { notes -> etNotes.setText(notes) }
            chExercise.exerciseId?.let { id -> viewModel.getExercise(id) } ?: run { error() }
        }
    }

    private fun setUpSeriesRV() {
        setUpSeriesData()
        binding.rvSeries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mAdapter = EditChExerciseAdapter(series, this@EditChExerciseDialog)
            adapter = mAdapter
        }
    }

    private fun setUpSeriesData() {
        if (series.isNotEmpty()) {
            for (i in 0 until series.size) {
                series[i].id = (i + 1).toString()
            }
        }
    }

    override fun addRepToSerie(id: String, reps: Int) {
        for (s in series) {
            if (s.id == id) s.reps = reps
        }
    }

    override fun addWeightToSerie(id: String, weight: Double) {
        for (s in series) {
            if (s.id == id) s.weight = weight
        }
    }

    override fun deleteSerie(id: String) {
        val iterator = series.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == id) {
                seriesCont--
                iterator.remove()
            }
        }
        setUpSeriesRV()
    }

    private fun endFlow() {
        listener.updateView()
        dismiss()
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            chExerciseId: String, listener: IMExerciseListener
        ): EditChExerciseDialog {
            return EditChExerciseDialog(chExerciseId, listener)
        }
    }
}