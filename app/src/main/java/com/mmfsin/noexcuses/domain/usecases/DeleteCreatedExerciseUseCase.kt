package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class DeleteCreatedExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<DeleteCreatedExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.deleteCustomExercise(params.createdExerciseId)

    data class Params(
        val createdExerciseId: String
    )
}