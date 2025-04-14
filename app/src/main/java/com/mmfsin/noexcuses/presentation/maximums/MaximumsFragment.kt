package com.mmfsin.noexcuses.presentation.maximums

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.presentation.maximums.adapter.MaximumsAdapter
import com.mmfsin.noexcuses.presentation.maximums.listeners.IMaximumsListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MaximumsFragment : BaseFragment<FragmentMaximumsBinding, MaximumsViewModel>(),
    IMaximumsListener {

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

    private fun setUpMaximums(data: List<MaximumData>) {
        binding.apply {
            rvMaximums.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = MaximumsAdapter(data, this@MaximumsFragment)
            }
            rvMaximums.isVisible = data.isNotEmpty()
            tvFavsEmpty.isVisible = data.isEmpty()
        }
    }

    override fun onSeeDetailsClick(exerciseId: String) {
        (activity as MainActivity).openBedRockActivity(
            R.navigation.nav_graph_maximums_detail,
            strArgs = exerciseId
        )
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}