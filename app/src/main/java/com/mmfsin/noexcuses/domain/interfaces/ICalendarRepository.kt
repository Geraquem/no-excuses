package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.CalendarInfo

interface ICalendarRepository {
    fun registerDayInCalendar(calendarInfo: CalendarInfo)

    fun getCalendarData(): List<String>
    fun getCalendarDayInfo(date: String): String
}