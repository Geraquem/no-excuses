package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.CompactExercise
import javax.inject.Inject

class GetDayExercisesUseCase @Inject constructor(
    private val repository: IExercisesRepository
) : BaseUseCase<GetDayExercisesUseCase.Params, List<CompactExercise>>() {

    override suspend fun execute(params: Params): List<CompactExercise> =
        repository.getDayExercises(params.dayId)

    data class Params(
        val dayId: String
    )
}