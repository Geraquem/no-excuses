package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.mappers.createChExercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import javax.inject.Inject

class AddChExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<AddChExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        params.dataChExercise.dataList?.let { data ->
            for (serie in data) {
                serie.exerciseDayId = params.idGroup.exerciseId + params.idGroup.dayId
            }
        }

        val chExercise = createChExercise(params.idGroup, params.dataChExercise)
        repository.addChExercise(chExercise)
    }

    data class Params(
        val idGroup: IdGroup,
        val dataChExercise: DataChExercise
    )
}