package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.MyRoutine
import javax.inject.Inject

class GetMyRoutineByIdUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<GetMyRoutineByIdUseCase.Params, MyRoutine?>() {

    override suspend fun execute(params: Params): MyRoutine? = repository.getRoutineById(params.id)

    data class Params(
        val id: String
    )
}