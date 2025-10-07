package com.mmfsin.noexcuses.domain.mappers

import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
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
        position = 0,
        superSerie = dataChExercise.superSerie
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
        position = chExercise.position,
        superSerie = dataChExercise.superSerie
    )
}

fun toExerciseDTO(newId: String? = null, o: Long, cE: CreatedExercise) = ExerciseDTO().apply {
    id = newId ?: UUID.randomUUID().toString()
    category = cE.category
    imageURL = cE.image ?: ""
    gifURL = cE.image
    name = cE.name
    order = o
    description = cE.description ?: ""
    muscles = cE.muscles ?: ""
    muscleWikiURL = cE.externalURL
    createdByUser = true
}