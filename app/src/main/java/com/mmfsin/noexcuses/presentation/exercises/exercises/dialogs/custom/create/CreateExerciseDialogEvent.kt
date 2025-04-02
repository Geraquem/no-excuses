package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.create

sealed class CreateExerciseDialogEvent {
    object Created : CreateExerciseDialogEvent()
    object SWW : CreateExerciseDialogEvent()
}