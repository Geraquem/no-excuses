package com.mmfsin.noexcuses.presentation.routines.dialogs

import com.mmfsin.noexcuses.domain.models.Routine

sealed class RoutineDialogEvent {
    object FlowCompleted : RoutineDialogEvent()
    class GetRoutine(val routine: Routine) : RoutineDialogEvent()
    object SomethingWentWrong : RoutineDialogEvent()
}