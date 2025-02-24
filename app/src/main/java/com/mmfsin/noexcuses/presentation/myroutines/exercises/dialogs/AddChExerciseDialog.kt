package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseDialog
import com.mmfsin.noexcuses.databinding.DialogChExerciseAddBinding
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.adapter.AddChExerciseAdapter
import com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.interfaces.IAddChExerciseListener
import com.mmfsin.noexcuses.presentation.myroutines.exercises.interfaces.IChExercisesListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class AddChExerciseDialog(
    private val idGroup: IdGroup,
    val listener: IChExercisesListener
) : BaseDialog<DialogChExerciseAddBinding>(), IAddChExerciseListener {

    private val viewModel: ChExerciseDialogViewModel by viewModels()

    private var mAdapter: AddChExerciseAdapter? = null

    private var exercise: Exercise? = null
    private var series = mutableListOf<Data>()

    override fun inflateView(inflater: LayoutInflater) =
        DialogChExerciseAddBinding.inflate(inflater)

    override fun setCustomViewDialog(dialog: Dialog) = centerViewDialog(dialog)

    override fun onResume() {
        super.onResume()
        requireDialog().animateDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
        idGroup.exerciseId?.let { id -> viewModel.getExercise(id) } ?: run { error() }
    }

    override fun setUI() {
        isCancelable = true
        binding.apply {
            exercise?.let {
                tvCategory.text = getString(R.string.exercise_dialog_category, it.category)
                tvName.text = it.name
                Glide.with(requireContext()).load(it.imageURL).into(image)
                setUpSeriesRV()
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAddSerie.setOnClickListener { addSerie() }

            btnAdd.setOnClickListener {
                btnAdd.isEnabled = false

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

                val data = DataChExercise(
                    dataList = mSeries,
                    time = restTime,
                    superSerie = swSuperSerie.isChecked,
                    notes = notesStr
                )
                viewModel.addChExercise(idGroup, data)
            }

            tvSeeExercise.setOnClickListener {
                exercise?.let { e -> listener.seeExercise(e.id) }
            }
        }
    }

    private fun addSerie() {
        series.add(Data(id = UUID.randomUUID().toString()))
        mAdapter?.notifyItemInserted(series.size - 1)
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ChExerciseDialogEvent.GetExercise -> {
                    this.exercise = event.exercise
                    setUI()
                    viewModel.getDay(idGroup.dayId)
                }

                is ChExerciseDialogEvent.GetDay -> {
                    binding.btnAdd.text =
                        getString(R.string.days_exercise_dialog_add, event.day.title)
                }

                is ChExerciseDialogEvent.GetChExercise -> {}
                is ChExerciseDialogEvent.FlowCompleted -> endFlow()
                is ChExerciseDialogEvent.SWW -> error()
            }
        }
    }

    private fun setUpSeriesRV() {
        binding.rvSeries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mAdapter = AddChExerciseAdapter(series, this@AddChExerciseDialog)
            adapter = mAdapter
        }
    }

    override fun addRepToSerie(id: String, reps: Int) {
        series.forEach { serie -> if (serie.id == id) serie.reps = reps }
    }

    override fun addWeightToSerie(id: String, weight: Double) {
        series.forEach { serie -> if (serie.id == id) serie.weight = weight }
    }

    override fun deleteSerie(id: String) {
        val iterator = series.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.id == id) {
                iterator.remove()
            }
        }
        setUpSeriesRV()
    }

    private fun endFlow() {
        binding.apply {
            listener.showSnackBar()
            dismiss()
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(idGroup: IdGroup, listener: IChExercisesListener): AddChExerciseDialog {
            return AddChExerciseDialog(idGroup, listener)
        }
    }
}