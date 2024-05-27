package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.mappers.createChExercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import javax.inject.Inject

class CheckExerciseFavUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<CheckExerciseFavUseCase.Params, Boolean>() {

    override suspend fun execute(params: Params): Boolean =
        repository.checkExerciseFav(params.exerciseId)

    data class Params(
        val exerciseId: String,
    )
}