package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.mappers.editChExercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import javax.inject.Inject

class EditChExerciseUseCase @Inject constructor(private val repository: IExercisesRepository) :
    BaseUseCase<EditChExerciseUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        val chExercise = repository.getChExerciseById(params.chExerciseId)
        chExercise?.let { chE ->
            params.dataChExercise.dataList?.let { data ->
                for (serie in data) {
                    serie.exerciseDayId = chE.exerciseId + chE.dayId
                }
            }

            val editedChExercise = editChExercise(chE, params.dataChExercise)
            println("----------------------------------------------------------------------------------------------------------")
            println("0.5->" + params.dataChExercise.superSerie)
            println("1->" + editedChExercise.superSerie)
            println("----------------------------------------------------------------------------------------------------------")

            repository.editChExercise(editedChExercise)
        }
    }

    data class Params(
        val chExerciseId: String,
        val dataChExercise: DataChExercise
    )
}