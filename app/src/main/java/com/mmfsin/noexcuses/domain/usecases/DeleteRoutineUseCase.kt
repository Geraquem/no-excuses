package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteRoutineUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCase<DeleteRoutineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteRoutine(params.id)

    data class Params(
        val id: String
    )
}