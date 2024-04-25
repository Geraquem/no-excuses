package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import javax.inject.Inject

class GetDefaultDayExercisesUseCase @Inject constructor(
    private val repository: IDefaultRoutinesRepository
) : BaseUseCase<GetDefaultDayExercisesUseCase.Params, List<DefaultExercise>>() {

    override suspend fun execute(params: Params): List<DefaultExercise> =
        repository.getDefaultExercises(params.dayId)

    data class Params(
        val dayId: String
    )
}