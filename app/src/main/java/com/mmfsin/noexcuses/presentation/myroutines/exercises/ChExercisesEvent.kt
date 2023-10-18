package com.mmfsin.noexcuses.presentation.myroutines.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ChExercisesEvent {
    class GetExercises(val exercises: List<Exercise>) : ChExercisesEvent()
    object SomethingWentWrong : ChExercisesEvent()
}