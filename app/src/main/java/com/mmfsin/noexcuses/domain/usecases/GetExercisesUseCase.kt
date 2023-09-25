package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<GetExercisesUseCase.Params, List<Exercise>>() {

    override suspend fun execute(params: Params): List<Exercise> =
        repository.getExercisesByMuscularGroup(params.mGroup)

    data class Params(
        val mGroup: String
    )
}