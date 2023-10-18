package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class DeleteChExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<DeleteChExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteChExercise(params.chExerciseId)

    data class Params(
        val chExerciseId: String
    )
}