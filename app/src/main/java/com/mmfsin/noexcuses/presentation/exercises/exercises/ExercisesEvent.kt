package com.mmfsin.noexcuses.presentation.exercises.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ExercisesEvent {
    class GetExercises(val exercises: List<Exercise>, val newCreated: Boolean) : ExercisesEvent()
    object SWW : ExercisesEvent()
}