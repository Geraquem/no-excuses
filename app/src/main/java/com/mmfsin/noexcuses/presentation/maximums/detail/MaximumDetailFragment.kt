package com.mmfsin.noexcuses.presentation.maximums.detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentMaximumsDetailBinding
import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.ExerciseDialog
import com.mmfsin.noexcuses.presentation.maximums.detail.adapter.MaximumDetailAdapter
import com.mmfsin.noexcuses.presentation.maximums.dialogs.add.AddMaxExerciseDialog
import com.mmfsin.noexcuses.presentation.maximums.dialogs.delete.DeleteMaxExerciseDialog
import com.mmfsin.noexcuses.presentation.maximums.dialogs.edit.EditMaxExerciseDialog
import com.mmfsin.noexcuses.presentation.maximums.listeners.IDialogsMaxExerciseListener
import com.mmfsin.noexcuses.presentation.maximums.listeners.IMaximumDetailListener
import com.mmfsin.noexcuses.utils.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import com.mmfsin.noexcuses.utils.sortedDateList
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MaximumDetailFragment :
    BaseFragment<FragmentMaximumsDetailBinding, MaximumDetailViewModel>(), IMaximumDetailListener,
    IDialogsMaxExerciseListener {

    override val viewModel: MaximumDetailViewModel by viewModels()

    private lateinit var mContext: Context
    private var exerciseId: String? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMaximumsDetailBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        exerciseId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseId?.let { id -> viewModel.getMaximumData(id) } ?: run { error() }
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(title = getString(R.string.maximums_title))
    }

    override fun setListeners() {
        binding.apply {
            llAddNew.setOnClickListener {
                exerciseId?.let { id ->
                    val dialog = AddMaxExerciseDialog(id, this@MaximumDetailFragment)
                    activity?.showFragmentDialog(dialog)
                }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MaximumDetailEvent.GetData -> setUpData(event.data)
                is MaximumDetailEvent.SWW -> error()
            }
        }
    }

    private fun setUpData(data: MaximumData) {
        binding.apply {
            Glide.with(mContext).load(data.exercise.gifURL).into(image)
            tvName.text = data.exercise.name

            val sortedList = data.data.sortedDateList()

            rvData.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MaximumDetailAdapter(sortedList, this@MaximumDetailFragment)
            }

            setBarChart(sortedList.reversed())
        }
    }

    override fun onMDataClick(id: String) {
        exerciseId?.let {
            activity?.showFragmentDialog(
                EditMaxExerciseDialog(
                    exerciseId = it,
                    mDataId = id,
                    this@MaximumDetailFragment
                )
            )
        }
    }

    override fun onSeeExerciseClick(exerciseId: String) {
        activity?.showFragmentDialog(ExerciseDialog(exerciseId))
    }

    override fun deleteMData(mDataId: String) {
        activity?.showFragmentDialog(DeleteMaxExerciseDialog(mDataId))
    }

    private fun setBarChart(mData: List<MData>) {
        binding.apply {
            val pesos = mData.map { it.weight }
            val fechas = mData.map { it.date }

            val entries = pesos.mapIndexed { index, peso ->
                BarEntry(index.toFloat(), peso.toFloat())
            }

            val barDataSet = BarDataSet(entries, "Peso")
            barDataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return if (value % 1f == 0f) {
                        "${value.toInt()}Kg"
                    } else {
                        "${value}Kg"
                    }
                }
            }

            barDataSet.color = Color.parseColor("#FF6200EE")
            barDataSet.valueTextColor = Color.BLACK
            barDataSet.valueTextSize = 12f

            val barData = BarData(barDataSet)
            barDataSet.valueTextSize = 14f
            barData.barWidth = 0.9f

            val chart = barChart
            chart.apply {
                data = barData
                setFitBars(true)
                setTouchEnabled(false)
                setScaleEnabled(false)
                setPinchZoom(false)
                // Eje X
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    valueFormatter = IndexAxisValueFormatter(fechas)
                    textColor = Color.BLACK

                    val inputFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
                    val outputFormat =
                        SimpleDateFormat("dd/MMM", Locale("es", "ES")) // Formato deseado

                    valueFormatter = object : IndexAxisValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            val index = value.toInt()
                            return if (index >= 0 && index < fechas.size) {
                                try {
                                    val date = inputFormat.parse(fechas[index])
                                    outputFormat.format(date ?: "")  // "02/Ene", "04/Abr", etc.
                                } catch (e: Exception) {
                                    fechas[index]  // fallback
                                }
                            } else {
                                ""
                            }
                        }
                    }

                }


                // Ejes Y

                axisLeft.textColor = Color.BLACK
                axisRight.isEnabled = false
                axisLeft.isEnabled = false

                axisLeft.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(true)
                }

                legend.isEnabled = false
                description.isEnabled = false
                animateY(750)

                invalidate()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}