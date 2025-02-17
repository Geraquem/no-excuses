package com.mmfsin.noexcuses.presentation.calendar

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetCalendarDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarDataUseCase: GetCalendarDataUseCase
) : BaseViewModel<CalendarEvent>() {

    fun getCalendarData() {
        executeUseCase(
            { getCalendarDataUseCase.execute() },
            { result -> _event.value = CalendarEvent.CalendarData(result) },
            { _event.value = CalendarEvent.SWW }
        )
    }
}