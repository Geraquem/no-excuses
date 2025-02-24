package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.mmfsin.noexcuses.domain.models.CalendarInfo

interface ICalendarRepository {
    fun registerDayInCalendar(calendarInfo: CalendarInfo)

    fun getCalendarData(): List<String>
    fun getCalendarDayInfo(date: String): List<CalendarDayData>
    fun deleteCalendarDayInfo(id: String)

    fun checkIfIsMyRoutine(routineId: String): Boolean?
}