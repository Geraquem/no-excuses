package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import io.realm.RealmList

fun ChExercise.toChExerciseDTO(actualPos: Int?) =
    ChExerciseDTO(
        id,
        routineId,
        dayId,
        exerciseId,
        data = setExerciseData(this.data),
        time,
        notes,
        position = actualPos ?: this.position
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