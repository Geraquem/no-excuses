package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import javax.inject.Inject

class CreateExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<CreateExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.createCustomExercise(params.createdExercise)

    data class Params(
        val createdExercise: CreatedExercise
    )
}