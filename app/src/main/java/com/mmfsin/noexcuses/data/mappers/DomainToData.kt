package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.domain.models.ChExercise

/** ChExercise */
fun ChExercise.toChExerciseDTO() =
    ChExerciseDTO(id, routineId, dayId, exerciseId, weight, series, reps, notes)

