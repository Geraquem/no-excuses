package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDaysUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCase<GetDaysUseCase.Params, List<Day>>() {

    override suspend fun execute(params: Params): List<Day> = repository.getDays(params.routineId)

    data class Params(
        val routineId: String
    )
}