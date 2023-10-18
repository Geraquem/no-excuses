package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class AddDayUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<AddDayUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.addDay(params.routineId, params.title)

    data class Params(
        val routineId: String,
        val title: String,
    )
}