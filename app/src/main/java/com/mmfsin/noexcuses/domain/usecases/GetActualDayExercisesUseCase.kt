package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class GetActualDayExercisesUseCase @Inject constructor(
    private val defaultRepository: IDefaultRoutinesRepository,
    private val exercisesRepository: IExercisesRepository,
) : BaseUseCase<GetActualDayExercisesUseCase.Params, List<Any>>() {

    override suspend fun execute(params: Params): List<Any> {
        return if (params.createdByUser) exercisesRepository.getDayExercises(params.dayId)
        else defaultRepository.getDefaultExercises(params.routineId, params.dayId)
    }

    data class Params(
        val routineId: String,
        val dayId: String,
        val createdByUser: Boolean
    )
}