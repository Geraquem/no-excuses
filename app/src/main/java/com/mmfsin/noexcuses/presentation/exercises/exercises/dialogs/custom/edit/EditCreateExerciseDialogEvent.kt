package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class EditCreateExerciseDialogEvent {
    class CreatedExercise(val exercise: Exercise) : EditCreateExerciseDialogEvent()
    object Edited : EditCreateExerciseDialogEvent()
    object SWW : EditCreateExerciseDialogEvent()
}