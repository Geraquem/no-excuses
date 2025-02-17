package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import javax.inject.Inject

class RegisterDayInCalendarUseCase @Inject constructor(
    private val repository: ICalendarRepository
) : BaseUseCase<RegisterDayInCalendarUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        repository.registerDayInCalendar(params.info)
    }

    data class Params(
        val info: CalendarInfo,
    )
}