package com.mmfsin.noexcuses.presentation.calendar

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentCalendarBinding
import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.mmfsin.noexcuses.presentation.calendar.adapter.CalendarDayAdapter
import com.mmfsin.noexcuses.presentation.calendar.dialogs.DeleteDayDataDialog
import com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises.CalendarDayExercisesSheet
import com.mmfsin.noexcuses.presentation.calendar.interfaces.ICalendarDayListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import com.mmfsin.noexcuses.utils.showFragmentDialog
import com.mmfsin.noexcuses.utils.toCompleteDateString
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarViewModel>(),
    ICalendarDayListener {

    override val viewModel: CalendarViewModel by viewModels()

    private lateinit var mContext: Context

    private var selectedDate: CalendarDay? = null

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
    }

    override fun setListeners() {
        binding.apply {
            calendar.setOnDateChangedListener { _, date, _ ->
                selectedDate = date
                viewModel.getDayInfo(date)
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CalendarEvent.CalendarData -> {
                    binding.calendar.addDecorator(MultipleDecorator(event.data))
                    selectedDate = CalendarDay.today()
                    viewModel.getDayInfo(CalendarDay.today())
                }

                is CalendarEvent.GetDayInfo -> setDayInfo(event.date, event.info)
                is CalendarEvent.DayDataDeleted -> {
                    selectedDate?.let { date -> viewModel.getDayInfo(date) }
                }

                is CalendarEvent.SWW -> error()
            }
        }
    }

    private fun setDayInfo(date: CalendarDay, info: List<CalendarDayData>) {
        binding.apply {
            tvSelectedDay.text = date.toCompleteDateString()
            tvEmpty.isVisible = info.isEmpty()
            llData.isVisible = info.isNotEmpty()
            if (info.isNotEmpty()) setRvData(info)
        }
    }

    private fun setRvData(info: List<CalendarDayData>) {
        binding.rvCalendarDayData.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = CalendarDayAdapter(info, this@CalendarFragment)
        }
    }

    override fun toExercises(dayId: String, routineId: String) {
        val dialog = CalendarDayExercisesSheet(dayId, routineId)
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    override fun deleteDayData(id: String) {
        activity?.showFragmentDialog(
            DeleteDayDataDialog.newInstance { viewModel.deleteCalendarDay(id) }
        )
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