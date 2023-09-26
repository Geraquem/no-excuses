package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDayExercisesUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<GetDayExercisesUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.getDayExercises(params.dayId)

    data class Params(
        val dayId: String
    )
}