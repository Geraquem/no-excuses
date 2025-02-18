package com.mmfsin.noexcuses.presentation.calendar

import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.prolificinteractive.materialcalendarview.CalendarDay

sealed class CalendarEvent {
    class CalendarData(val data: List<CalendarDay>) : CalendarEvent()
    class GetDayInfo(val date: CalendarDay, val info: List<CalendarDayData>) : CalendarEvent()
    object DayDataDeleted : CalendarEvent()
    object SWW : CalendarEvent()
}