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
        return days.toCalendarDayList()
    }
}