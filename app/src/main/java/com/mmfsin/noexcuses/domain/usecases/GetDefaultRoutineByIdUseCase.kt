package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import com.mmfsin.noexcuses.domain.models.MyRoutine
import javax.inject.Inject

class GetDefaultRoutineByIdUseCase @Inject constructor(private val repository: IDefaultRoutinesRepository) :
    BaseUseCase<GetDefaultRoutineByIdUseCase.Params, DefaultRoutine?>() {

    override suspend fun execute(params: Params): DefaultRoutine? = repository.getDefaultRoutineById(params.id)

    data class Params(
        val id: String
    )
}