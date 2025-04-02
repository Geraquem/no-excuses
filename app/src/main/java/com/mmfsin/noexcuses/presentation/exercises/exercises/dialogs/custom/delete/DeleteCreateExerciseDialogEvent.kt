package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.delete

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class DeleteCreateExerciseDialogEvent {
    class CreatedExercise(val exercise: Exercise) : DeleteCreateExerciseDialogEvent()
    object Deleted : DeleteCreateExerciseDialogEvent()
    object SWW : DeleteCreateExerciseDialogEvent()
}