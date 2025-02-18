package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.data.mappers.toDateString
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.prolificinteractive.materialcalendarview.CalendarDay
import javax.inject.Inject

class GetCalendarDayInfoUseCase @Inject constructor(
    private val repository: ICalendarRepository
) : BaseUseCase<GetCalendarDayInfoUseCase.Params, String>() {

    override suspend fun execute(params: Params): String =
        repository.getCalendarDayInfo(params.date.toDateString())

    data class Params(
        val date: CalendarDay
    )
}