package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toCalendarInfoDTO
import com.mmfsin.noexcuses.data.models.CalendarInfoDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.utils.DATE
import com.mmfsin.noexcuses.utils.ID
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

    override fun getCalendarDayInfo(date: String): List<CalendarDayData> {
        val info = realmDatabase.getObjectsFromRealm {
            where<CalendarInfoDTO>().equalTo(DATE, date).findAll()
        }
        val result = mutableListOf<CalendarDayData>()
        info.forEach { i ->
            val data = getCalendarDayData(i.id, i.routineId, i.dayId)
            result.add(data)
        }
        return result
    }

    private fun getCalendarDayData(id: String, routineId: String, dayId: String): CalendarDayData {
        val mRoutine = realmDatabase.getObjectFromRealm(MyRoutineDTO::class.java, ID, routineId)
        val dfRoutine =
            realmDatabase.getObjectFromRealm(DefaultRoutineDTO::class.java, ID, routineId)
        val day = realmDatabase.getObjectFromRealm(DayDTO::class.java, ID, dayId)

        return CalendarDayData(
            databaseId = id,
            dayName = day?.title,
            dayId = dayId,
            routineName = mRoutine?.title ?: (dfRoutine?.name),
            routineDescription = mRoutine?.description ?: dfRoutine?.description,
            routineId = routineId
        )
    }

    override fun deleteCalendarDayInfo(id: String) {
        realmDatabase.deleteObject(CalendarInfoDTO::class.java, ID, id)
    }
}