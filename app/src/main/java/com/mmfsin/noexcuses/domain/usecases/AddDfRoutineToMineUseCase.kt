package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.data.mappers.createNewRoutineFromDefault
import com.mmfsin.noexcuses.data.mappers.toChExerciseDTO
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import java.util.UUID
import javax.inject.Inject

class AddDfRoutineToMineUseCase @Inject constructor(
    private val dfRepository: IDefaultRoutinesRepository,
    private val mRepository: IMyRoutinesRepository,
    private val eRepository: IExercisesRepository
) : BaseUseCase<AddDfRoutineToMineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        val dfRoutine = dfRepository.getDefaultRoutineById(params.dfRoutineId)
        dfRoutine?.let { r ->
            val newRoutineId = UUID.randomUUID().toString()

            val days = dfRepository.getDefaultDays(params.dfRoutineId)
            days.forEach { day ->
                val newDayId = UUID.randomUUID().toString()

                /** Sacamos días de la rutina */
                val dayExercises = dfRepository.getDefaultExercises(params.dfRoutineId, day.id)
                dayExercises.forEachIndexed { pos, e ->
                    val newExerciseId = UUID.randomUUID().toString()

                    /** Para cada día, sacamos sus ejercicios */
                    val chExerciseDTO = e.toChExerciseDTO(
                        newExerciseId,
                        newRoutineId,
                        newDayId,
                        pos
                    )

                    /** Guardamos el ejercicio con sus series */
                    eRepository.addDefaultExerciseAsMine(chExerciseDTO)
                }
                /** Guardamos día */
                mRepository.addDayToNewDfRoutineMine(day, newDayId, newRoutineId)
            }

            /** Guardamos rutina */
            val newRoutine = r.createNewRoutineFromDefault(newRoutineId, days.size)
            mRepository.addDfRoutineToMine(newRoutine)

        } ?: run { throw NullPointerException() }
    }

    data class Params(
        val dfRoutineId: String,
    )
}