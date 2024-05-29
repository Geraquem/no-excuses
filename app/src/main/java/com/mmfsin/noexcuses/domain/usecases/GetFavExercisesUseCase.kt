package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import javax.inject.Inject

class GetFavExercisesUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCaseNoParams<List<Exercise>>() {

    override suspend fun execute(): List<Exercise> = repository.getFavExercises()
}