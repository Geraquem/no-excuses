package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import javax.inject.Inject

class DeleteCalendarDayUseCase @Inject constructor(private val repository: ICalendarRepository) :
    BaseUseCase<DeleteCalendarDayUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteCalendarDayInfo(params.id)

    data class Params(
        val id: String
    )
}