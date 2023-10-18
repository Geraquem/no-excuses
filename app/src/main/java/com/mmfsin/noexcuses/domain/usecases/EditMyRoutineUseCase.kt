package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class EditMyRoutineUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<EditMyRoutineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        val description = if (params.description.isEmpty() || params.description.isBlank()) null
        else params.description
        repository.editRoutine(params.id, params.title, description)
    }

    data class Params(
        val id: String,
        val title: String,
        val description: String
    )
}