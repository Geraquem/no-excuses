package com.mmfsin.noexcuses.presentation.calendar

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetCalendarDataUseCase
import com.mmfsin.noexcuses.domain.usecases.GetCalendarDayInfoUseCase
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDataUseCase: GetCalendarDataUseCase,
    private val getCalendarDayInfoUseCase: GetCalendarDayInfoUseCase
) : BaseViewModel<CalendarEvent>() {

    fun getCalendarData() {
        executeUseCase(
            { getCalendarDataUseCase.execute() },
            { result -> _event.value = CalendarEvent.CalendarData(result) },
            { _event.value = CalendarEvent.SWW }
        )
    }

    fun getDayInfo(date: CalendarDay) {
        executeUseCase(
            { getCalendarDayInfoUseCase.execute(GetCalendarDayInfoUseCase.Params(date)) },
            { result -> _event.value = CalendarEvent.GetDayInfo(date, result) },
            { _event.value = CalendarEvent.SWW }
        )
    }
}