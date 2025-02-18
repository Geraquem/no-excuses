package com.mmfsin.noexcuses.presentation.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay

sealed class CalendarEvent {
    class CalendarData(val data: List<CalendarDay>) : CalendarEvent()
    class GetDayInfo(val info: String) : CalendarEvent()
    object SWW : CalendarEvent()
}