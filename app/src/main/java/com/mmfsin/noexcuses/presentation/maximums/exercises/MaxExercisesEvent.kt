package com.mmfsin.noexcuses.presentation.maximums.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class MaxExercisesEvent {
    class GetExercises(val exercises: List<Exercise>, val newCreated: Boolean) : MaxExercisesEvent()
    object SWW : MaxExercisesEvent()
}