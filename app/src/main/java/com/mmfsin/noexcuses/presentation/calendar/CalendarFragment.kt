package com.mmfsin.noexcuses.presentation.calendar

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.base.bedrock.BedRockActivity
import com.mmfsin.noexcuses.databinding.FragmentCalendarBinding
import com.mmfsin.noexcuses.utils.getMonthName
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>() {

    override val viewModel: CalendarViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCalendarBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCalendarData()
    }

    override fun setUI() {
        binding.apply {
            calendar.addDecorator(TodayDecorator())
        }
        (activity as BedRockActivity).apply {
            setUpToolbar(title = getString(R.string.calendar_title))
        }
    }

    override fun setListeners() {
        binding.apply {
            calendar.setOnDateChangedListener { _, date, _ ->
                val dayToSearch = "${date.day}/${date.month}/${date.year}"
                viewModel.getDayInfo(dayToSearch)
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CalendarEvent.CalendarData -> {
                    binding.calendar.addDecorator(MultipleDecorator(event.data))
                    setTodayDate()
                }

                is CalendarEvent.GetDayInfo -> {
                    binding.tvAux.text = event.info
                }

                is CalendarEvent.SWW -> error()
            }
        }
    }

    private fun setTodayDate() {
        val today = CalendarDay.today()
        val text = "${today.day} de ${(today.month).getMonthName()} de ${today.year}"
        binding.tvSelectedDay.text = text

        val search = "17/2/2025"
        viewModel.getDayInfo(date = search)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    inner class TodayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return CalendarDay.today() == day
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(ForegroundColorSpan(Color.BLUE))
        }
    }

    inner class MultipleDecorator(private val dates: List<CalendarDay>) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(DotSpan(8f, Color.RED))
        }
    }
}