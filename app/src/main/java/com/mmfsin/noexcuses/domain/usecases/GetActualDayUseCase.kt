package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetActualDayUseCase @Inject constructor(
    private val defaultRepository: IDefaultRoutinesRepository,
    private val myRoutinesRepository: IMyRoutinesRepository
) :
    BaseUseCase<GetActualDayUseCase.Params, Day?>() {

    override suspend fun execute(params: Params): Day? {
        return if (params.createdByUser) myRoutinesRepository.getDayById(params.dayId)
        else defaultRepository.getDefaultDayById(params.dayId)
    }

    data class Params(
        val dayId: String,
        val createdByUser: Boolean
    )
}