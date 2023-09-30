package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.*
import com.mmfsin.noexcuses.domain.models.*

/** MuscularGroup */
fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(name, imageURL)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

/** Exercise */
fun ExerciseDTO.toExercise() = Exercise(id, category, imageURL, name, dataURL)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }

/** Routine */
fun RoutineDTO.toRoutine() = Routine(id, title, description, days)

fun List<RoutineDTO>.toRoutineList() = this.map { it.toRoutine() }

/** Day */
fun DayDTO.toDay() = Day(id, routineId, title, exercises)

fun List<DayDTO>.toDayList() = this.map { it.toDay() }

/** ChExercise */
fun ChExerciseDTO.toChExercise() =
    ChExercise(id, routineId, dayId, exerciseId, weight, series, reps, notes)

fun List<ChExerciseDTO>.toChExerciseList() = this.map { it.toChExercise() }

fun ChExerciseDTO.toCompactExercise(exercise: Exercise) =
    CompactExercise(id, exercise.name, exercise.category, exercise.imageURL, weight, series, reps)