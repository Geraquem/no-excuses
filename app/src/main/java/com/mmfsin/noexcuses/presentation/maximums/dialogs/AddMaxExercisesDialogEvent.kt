package com.mmfsin.noexcuses.presentation.maximums.dialogs

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class AddMaxExercisesDialogEvent {
    class GetExercise(val exercise: Exercise) : AddMaxExercisesDialogEvent()
    object Registered : AddMaxExercisesDialogEvent()
    object SWW : AddMaxExercisesDialogEvent()
}