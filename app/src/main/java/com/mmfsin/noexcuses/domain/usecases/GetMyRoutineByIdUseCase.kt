package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetMyRoutineByIdUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<GetMyRoutineByIdUseCase.Params, Routine?>() {

    override suspend fun execute(params: Params): Routine? = repository.getRoutineById(params.id)

    data class Params(
        val id: String
    )
}