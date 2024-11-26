package com.mmfsin.noexcuses.domain.mappers

import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import java.util.UUID

fun createChExercise(idGroup: IdGroup, dataChExercise: DataChExercise): ChExercise {
    return ChExercise(
        id = UUID.randomUUID().toString(),
        routineId = idGroup.routineId,
        dayId = idGroup.dayId,
        exerciseId = idGroup.exerciseId,
        data = dataChExercise.dataList,
        time = dataChExercise.time,
        notes = dataChExercise.notes,
        position = 0
    )
}

fun editChExercise(chExercise: ChExercise, dataChExercise: DataChExercise): ChExercise {
    return ChExercise(
        id = chExercise.id,
        routineId = chExercise.routineId,
        dayId = chExercise.dayId,
        exerciseId = chExercise.exerciseId,
        data = dataChExercise.dataList,
        time = dataChExercise.time,
        notes = dataChExercise.notes,
        position = chExercise.position
    )
}