package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ChExerciseDialogEvent {
    class GetExercise(val exercise: Exercise) : ChExerciseDialogEvent()
    class GetDay(val day: Day) : ChExerciseDialogEvent()
    class GetChExercise(val chExercise: ChExercise) : ChExerciseDialogEvent()
    object FlowCompleted : ChExerciseDialogEvent()
    object SomethingWentWrong : ChExerciseDialogEvent()
}