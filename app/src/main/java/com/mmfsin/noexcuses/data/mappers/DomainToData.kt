package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Routine
import io.realm.RealmList
import java.util.UUID

fun ChExercise.toChExerciseDTO(actualPos: Int?) =
    ChExerciseDTO(
        id,
        routineId,
        dayId,
        exerciseId,
        data = setExerciseData(this.data),
        time,
        notes,
        position = actualPos ?: this.position,
        superSerie = this.superSerie
    )

fun setExerciseData(data: List<Data>?): RealmList<DataDTO>? {
    return data?.let {
        val list = RealmList<DataDTO>()
        for (d in data) {
            list.add(d.toDataDTO())
        }
        list
    } ?: run { null }
}

fun Data.toDataDTO() = DataDTO(id, exerciseDayId, reps, weight)

fun Routine.createNewRoutineFromDefault(newId: String, days: Int) = MyRoutineDTO(
    id = newId,
    title = this.name,
    description = this.description,
    days = days
)

fun DefaultExercise.toChExerciseDTO(
    newRoutineId: String,
    newDayId: String,
    position: Int
) = ChExerciseDTO(
    id = UUID.randomUUID().toString(),
    routineId = newRoutineId,
    dayId = newDayId,
    exerciseId = this.exercise.id,
    data = null,
    time = this.desc.toDouble(),
    notes = null,
    position = position,
    superSerie = this.superSerie
)