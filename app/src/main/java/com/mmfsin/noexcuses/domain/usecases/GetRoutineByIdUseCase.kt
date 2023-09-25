package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetRoutineByIdUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCase<GetRoutineByIdUseCase.Params, Routine?>() {

    override suspend fun execute(params: Params): Routine? = repository.getRoutineById(params.id)

    data class Params(
        val id: String
    )
}