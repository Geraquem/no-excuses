package com.mmfsin.noexcuses.presentation.exercises.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ExercisesEvent {
    class GetExercises(val exercises: List<Exercise>) : ExercisesEvent()
    object SomethingWentWrong : ExercisesEvent()
}