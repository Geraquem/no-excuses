package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetDefaultRoutinesUseCase @Inject constructor(
    private val exercisesRepository: ExercisesRepository,
    private val dfRoutinesRepository: IDefaultRoutinesRepository
) : BaseUseCaseNoParams<List<Routine>>() {

    override suspend fun execute(): List<Routine> {
        exercisesRepository.getExercises()
        return dfRoutinesRepository.getDefaultRoutines()
    }
}