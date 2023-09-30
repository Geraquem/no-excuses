package com.mmfsin.noexcuses.domain.mappers

import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import java.util.*

fun createChExercise(idGroup: IdGroup, dataChExercise: DataChExercise): ChExercise {
    return ChExercise(
        id = UUID.randomUUID().toString(),
        routineId = idGroup.routineId,
        dayId = idGroup.dayId,
        exerciseId = idGroup.exerciseId,
        weight = dataChExercise.weight,
        series = dataChExercise.series,
        reps = dataChExercise.reps,
        notes = dataChExercise.notes
    )
}