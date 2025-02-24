package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class GetCalendarDayExercisesUseCase @Inject constructor(
    private val calendarRepository: ICalendarRepository,
    private val exercisesRepository: IExercisesRepository,
    private val defaultRepository: IDefaultRoutinesRepository,
) : BaseUseCase<GetCalendarDayExercisesUseCase.Params, List<Any>>() {

    override suspend fun execute(params: Params): List<Any> {
        val createdByUser = calendarRepository.checkIfIsMyRoutine(params.routineId)
        return createdByUser?.let { mine ->
            if (mine) exercisesRepository.getDayExercises(params.dayId)
            else defaultRepository.getDefaultExercises(params.routineId, params.dayId)
        } ?: run { throw NullPointerException() }
    }

    data class Params(
        val routineId: String,
        val dayId: String,
    )
}