package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

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
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDayExerciseListener
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.adapter.EditChExerciseAdapter
import com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.interfaces.IAddChExerciseListener
import com.mmfsin.noexcuses.utils.animateDialog
import com.mmfsin.noexcuses.utils.formatTime
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditChExerciseDialog(
    private val chExerciseId: String, private val listener: IDayExerciseListener
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
            lottie.visibility = View.GONE
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
                is ChExerciseDialogEvent.AddedCompleted -> endFlow()
                is ChExerciseDialogEvent.SomethingWentWrong -> error()
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
            chExercise.time?.let { time -> etTime.hint = time.formatTime() }
            chExercise.notes?.let { notes -> etNotes.setText(notes) }
            chExercise.exerciseId?.let { id -> viewModel.getExercise(id) } ?: run { error() }
        }
    }

    private fun setUpSeriesRV() {
        binding.rvSeries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mAdapter = EditChExerciseAdapter(series, this@EditChExerciseDialog)
            adapter = mAdapter
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

    private fun endFlow() {
        binding.apply {
            lottie.visibility = View.VISIBLE
            lottie.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    dismiss()
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })

            lottie.setAnimation(R.raw.flow_completed)
            lottie.playAnimation()
        }
    }

    private fun error() = activity?.showErrorDialog()

    companion object {
        fun newInstance(
            chExerciseId: String, listener: IDayExerciseListener
        ): EditChExerciseDialog {
            return EditChExerciseDialog(chExerciseId, listener)
        }
    }
}