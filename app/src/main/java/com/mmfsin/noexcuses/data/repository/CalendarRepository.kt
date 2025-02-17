package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toCalendarInfoDTO
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : ICalendarRepository {

    override suspend fun registerDayInCalendar(calendarInfo: CalendarInfo) {
        realmDatabase.addObject { calendarInfo.toCalendarInfoDTO() }
    }
}