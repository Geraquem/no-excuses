package com.mmfsin.noexcuses.presentation.routines.days.fragments

sealed class DayConfigEvent {
    object FlowCompleted : DayConfigEvent()
//    class GetRoutine(val routine: Routine) : DayConfigEvent()
    object SomethingWentWrong : DayConfigEvent()
}