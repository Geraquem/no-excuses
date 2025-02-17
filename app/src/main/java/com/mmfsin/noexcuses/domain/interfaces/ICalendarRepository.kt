package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.CalendarInfo

interface ICalendarRepository {
    suspend fun registerDayInCalendar(calendarInfo: CalendarInfo)
    suspend fun getCalendarData(): List<String>
}