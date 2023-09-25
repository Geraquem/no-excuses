package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import javax.inject.Inject

class GetMuscularGroupsUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCaseNoParams<List<MuscularGroup>>() {

    override suspend fun execute(): List<MuscularGroup> = repository.getMuscularGroups()
}