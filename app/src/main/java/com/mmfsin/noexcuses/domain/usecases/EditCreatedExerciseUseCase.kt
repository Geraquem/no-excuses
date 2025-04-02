package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import javax.inject.Inject

class EditCreatedExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<EditCreatedExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editCustomExercise(params.createdExercise, params.id)

    data class Params(
        val createdExercise: CreatedExercise,
        val id: String,
    )
}