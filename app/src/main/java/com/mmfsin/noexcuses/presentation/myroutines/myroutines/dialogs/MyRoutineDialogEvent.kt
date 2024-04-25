package com.mmfsin.noexcuses.presentation.myroutines.myroutines.dialogs

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MyRoutineDialogEvent {
    object FlowCompleted : MyRoutineDialogEvent()
    class GetMyRoutine(val routine: Routine) : MyRoutineDialogEvent()
    object SWW : MyRoutineDialogEvent()
}