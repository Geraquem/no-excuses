package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class UpdateExerciseFavUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<UpdateExerciseFavUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.updateExerciseFav(params.exerciseId)

    data class Params(
        val exerciseId: String,
    )
}