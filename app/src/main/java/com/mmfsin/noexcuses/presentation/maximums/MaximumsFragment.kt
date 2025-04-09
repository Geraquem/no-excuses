package com.mmfsin.noexcuses.presentation.maximums

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mmfsin.noexcuses.MainActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMaximumsBinding
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MaximumsFragment : BaseFragment<FragmentMaximumsBinding, MaximumsViewModel>() {

    override val viewModel: MaximumsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMaximumsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMaximumData()
    }

    override fun setListeners() {
        binding.apply {
            btnAddMaximum.setOnClickListener {
                (activity as MainActivity).openBedRockActivity(R.navigation.nav_graph_maximums)
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MaximumsEvent.GetMaximums -> setUpMaximums(event.data)
                is MaximumsEvent.SWW -> error()
            }
        }
    }

    private fun setUpMaximums(data: List<String>) {
//        setBarChart()
        binding.apply {
            tvFavsEmpty.isVisible = data.isEmpty()
        }
    }

    private fun setBarChart() {
        binding.apply {
            val pesos = listOf(70.2, 70.5, 69.0, 71.00, 72.26)
            val fechas = listOf("01/01", "02/02", "03/03", "04/04", "05/04")

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