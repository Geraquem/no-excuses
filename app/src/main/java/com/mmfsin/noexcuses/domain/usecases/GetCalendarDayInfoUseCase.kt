package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.mmfsin.noexcuses.utils.toDateString
import com.prolificinteractive.materialcalendarview.CalendarDay
import javax.inject.Inject

class GetCalendarDayInfoUseCase @Inject constructor(
    private val repository: ICalendarRepository
) : BaseUseCase<GetCalendarDayInfoUseCase.Params, List<CalendarDayData>>() {

    override suspend fun execute(params: Params): List<CalendarDayData> =
        repository.getCalendarDayInfo(params.date.toDateString())

    data class Params(
        val date: CalendarDay
    )
}