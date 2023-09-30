package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Exercise

sealed class ChExerciseDialogEvent {
    class GetExercise(val exercise: Exercise) : ChExerciseDialogEvent()
    class GetDay(val day: Day) : ChExerciseDialogEvent()
    object AddedCompleted : ChExerciseDialogEvent()
    object SomethingWentWrong : ChExerciseDialogEvent()
}