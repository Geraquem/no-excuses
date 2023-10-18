package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class AddMyRoutineUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<AddMyRoutineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        val description = if (params.description.isEmpty() || params.description.isBlank()) null
        else params.description
        repository.addRoutine(params.title, description)
    }

    data class Params(
        val title: String,
        val description: String
    )
}