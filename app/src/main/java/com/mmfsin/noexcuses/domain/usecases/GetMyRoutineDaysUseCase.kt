package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetMyRoutineDaysUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<GetMyRoutineDaysUseCase.Params, List<Day>>() {

    override suspend fun execute(params: Params): List<Day> = repository.getRoutineDays(params.routineId)

    data class Params(
        val routineId: String
    )
}