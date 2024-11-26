package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class MoveChExerciseUseCase @Inject constructor(
    private val repository: IExercisesRepository
) : BaseUseCase<MoveChExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.moveChExercise(params.exercises)

    data class Params(
        val exercises: List<String>,
    )
}