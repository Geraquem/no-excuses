package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MyRoutineDialogEvent {
    object FlowCompleted : MyRoutineDialogEvent()
    class GetMyRoutine(val routine: Routine) : MyRoutineDialogEvent()
    object SomethingWentWrong : MyRoutineDialogEvent()
}