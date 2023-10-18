package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class DeleteMyRoutineUseCase @Inject constructor(
    private val routinesRepository: IMyRoutinesRepository,
    private val exercisesRepository: IExercisesRepository
) : BaseUseCase<DeleteMyRoutineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        /** delete all exercises in routine */
        exercisesRepository.deleteExercisesFromDeletedRoutine(params.id)
        /** iniside deleteRoutine also delete days */
        routinesRepository.deleteRoutine(params.id)
    }

    data class Params(
        val id: String
    )
}