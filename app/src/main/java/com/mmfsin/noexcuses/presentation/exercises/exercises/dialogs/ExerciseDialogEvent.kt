package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ExerciseDialogEvent {
    class GetExercise(val exercise: Exercise) : ExerciseDialogEvent()
    object SWW : ExerciseDialogEvent()
}