package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.data.mappers.toCalendarDayList
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import javax.inject.Inject

class GetCalendarDataUseCase @Inject constructor(
    private val repository: ICalendarRepository
) : BaseUseCaseNoParams<List<CalendarDay>>() {
    override suspend fun execute(): List<CalendarDay> {
        val days = repository.getCalendarData()
        println("-----------------------------------------------------------------------------------------------------------------------------------------------")
        println("1 -> $days")
        println("-----------------------------------------------------------------------------------------------------------------------------------------------")

        val a = days.toCalendarDayList()
        println("-----------------------------------------------------------------------------------------------------------------------------------------------")
        println("2 -> $a")
        println("-----------------------------------------------------------------------------------------------------------------------------------------------")

        return days.toCalendarDayList()
    }
}