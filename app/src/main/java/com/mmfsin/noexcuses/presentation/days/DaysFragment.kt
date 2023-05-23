package com.mmfsin.noexcuses.presentation.days

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentDaysBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.days.DaysFragmentDirections.Companion.actionDaysToDayExercises
import com.mmfsin.noexcuses.presentation.days.adapter.DayAdapter
import com.mmfsin.noexcuses.presentation.days.dialog.configday.ConfigDayDialog
import com.mmfsin.noexcuses.presentation.days.dialog.deleteday.DeleteDayDialog
import com.mmfsin.noexcuses.presentation.days.dialog.newday.NewDayDialog
import com.mmfsin.noexcuses.presentation.days.interfaces.IConfigDayListener
import com.mmfsin.noexcuses.presentation.days.interfaces.IDayListener

class DaysFragment() : BaseFragment<FragmentDaysBinding>(), DaysView, IDayListener,
    IConfigDayListener {

    private val presenter by lazy { DaysPresenter(this) }

    private lateinit var mContext: Context

    private var phase: Phase? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDaysBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phase?.let { presenter.getDays(it.id) } ?: run {
            activity?.onBackPressed()
            sww()
        }
    }

    private fun getBundleArgs() =
        arguments?.let { bundle -> phase = bundle.getSerializable("phase") as Phase }

    override fun setUI() {
        getBundleArgs()
        binding.apply {
            phase?.let { phase ->
                toolbar.title.text = phase.name
                phase.description?.let { description ->
                    tvPhaseDescription.text = description
                    tvPhaseDescription.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }
            addDay.setOnClickListener {
                phase?.let { phase ->
                    val dialog = NewDayDialog(null, phase.id) { presenter.getDays(it) }
                    activity?.let { dialog.show(it.supportFragmentManager, "") }
                }
            }
        }
    }

    override fun getDays(days: List<Day>) {
        binding.nothingYet.root.visibility = if (days.isEmpty()) View.VISIBLE else View.GONE
        binding.rvDays.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = DayAdapter(days, this@DaysFragment)
        }
    }

    override fun dayDeleted() {
        phase?.let { presenter.getDays(it.id) }
    }

    override fun onClick(day: Day) =
        findNavController().navigate(actionDaysToDayExercises(day.name, day.id))

    override fun sww() {
        Toast.makeText(
            this@DaysFragment.requireContext(), getString(R.string.sww), Toast.LENGTH_SHORT
        ).show()
    }

    override fun config(day: Day) {
        val dialog = ConfigDayDialog(day, this)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun edit(day: Day) {
        phase?.let { phase ->
            val dialog = NewDayDialog(day, phase.id) { presenter.getDays(phase.id) }
            activity?.let { dialog.show(it.supportFragmentManager, "") }
        }
    }

    override fun delete(day: Day) {
        val dialog = DeleteDayDialog(day) { presenter.deleteDay(it) }
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}