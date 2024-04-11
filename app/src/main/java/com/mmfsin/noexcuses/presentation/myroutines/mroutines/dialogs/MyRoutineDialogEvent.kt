package com.mmfsin.noexcuses.presentation.myroutines.mroutines.dialogs

import com.mmfsin.noexcuses.domain.models.MyRoutine

sealed class MyRoutineDialogEvent {
    object FlowCompleted : MyRoutineDialogEvent()
    class GetMyRoutine(val myRoutine: MyRoutine) : MyRoutineDialogEvent()
    object SomethingWentWrong : MyRoutineDialogEvent()
}