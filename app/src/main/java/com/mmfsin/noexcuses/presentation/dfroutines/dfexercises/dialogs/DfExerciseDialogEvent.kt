package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.dialogs

import com.mmfsin.noexcuses.domain.models.DefaultExercise

sealed class DfExerciseDialogEvent {
    class GetDefaultExercise(val exercise: DefaultExercise) : DfExerciseDialogEvent()
    object SWW : DfExerciseDialogEvent()
}