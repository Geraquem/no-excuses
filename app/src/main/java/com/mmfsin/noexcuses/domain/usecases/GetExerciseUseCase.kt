package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import javax.inject.Inject

class GetExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<GetExerciseUseCase.Params, Exercise?>() {

    override suspend fun execute(params: Params): Exercise? = repository.getExerciseById(params.id)

    data class Params(
        val id: String
    )
}