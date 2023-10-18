package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDayByIdUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<GetDayByIdUseCase.Params, Day?>() {

    override suspend fun execute(params: Params): Day? = repository.getDayById(params.dayId)

    data class Params(
        val dayId: String
    )
}