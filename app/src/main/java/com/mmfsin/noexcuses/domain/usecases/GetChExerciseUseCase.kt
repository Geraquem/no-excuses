package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.ChExercise
import javax.inject.Inject

class GetChExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<GetChExerciseUseCase.Params, ChExercise?>() {

    override suspend fun execute(params: Params): ChExercise? =
        repository.getChExercise(params.chExerciseId)

    data class Params(
        val chExerciseId: String
    )
}