package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup

fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(name, imageURL)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

fun ExerciseDTO.toExercise() = Exercise(id, category, imageURL, name, dataURL)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }
