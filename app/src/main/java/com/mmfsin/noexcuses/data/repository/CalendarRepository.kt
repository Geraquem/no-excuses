package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toCalendarInfoDTO
import com.mmfsin.noexcuses.data.models.CalendarInfoDTO
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.utils.DATE
import io.realm.kotlin.where
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : ICalendarRepository {

    override fun registerDayInCalendar(calendarInfo: CalendarInfo) {
        realmDatabase.addObject { calendarInfo.toCalendarInfoDTO() }
    }

    override fun getCalendarData(): List<String> {
        val days = realmDatabase.getObjectsFromRealm { where<CalendarInfoDTO>().findAll() }
        val result = mutableListOf<String>()
        days.forEach { d -> result.add(d.date) }
        return result
    }

    override fun getCalendarDayInfo(date: String): String {
        val info = realmDatabase.getObjectsFromRealm {
            where<CalendarInfoDTO>().equalTo(DATE, date).findAll()
        }
        return if (info.isNotEmpty()) info.last().routineId else "no hay nada"
    }
}