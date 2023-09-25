package com.mmfsin.noexcuses.presentation.exercises.exercises.dialog

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ExerciseDialogEvent {
    class GetExercise(val exercise: Exercise) : ExerciseDialogEvent()
    object SomethingWentWrong : ExerciseDialogEvent()
}