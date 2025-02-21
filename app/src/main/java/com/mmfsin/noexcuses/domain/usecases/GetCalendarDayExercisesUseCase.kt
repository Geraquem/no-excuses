package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class GetCalendarDayExercisesUseCase @Inject constructor(
    private val defaultRepository: IDefaultRoutinesRepository,
    private val exercisesRepository: IExercisesRepository,
) : BaseUseCase<GetCalendarDayExercisesUseCase.Params, List<Any>>() {

    override suspend fun execute(params: Params): List<Any> {
        return emptyList()
    }

    data class Params(
        val routineId: String,
        val dayId: String,
    )
}