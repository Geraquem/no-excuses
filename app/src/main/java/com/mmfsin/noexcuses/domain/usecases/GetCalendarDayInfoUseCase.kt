package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import javax.inject.Inject

class GetCalendarDayInfoUseCase @Inject constructor(
    private val repository: ICalendarRepository
) : BaseUseCase<GetCalendarDayInfoUseCase.Params, String>() {

    override suspend fun execute(params: Params): String =
        repository.getCalendarDayInfo(params.date)

    data class Params(
        val date: String
    )
}