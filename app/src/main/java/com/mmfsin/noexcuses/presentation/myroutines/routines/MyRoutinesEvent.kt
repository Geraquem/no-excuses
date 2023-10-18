package com.mmfsin.noexcuses.presentation.myroutines.routines

import com.mmfsin.noexcuses.domain.models.MyRoutine

sealed class MyRoutinesEvent {
    class IsFistTime(val firstTime: Boolean) : MyRoutinesEvent()
    class GetMyRoutines(val myRoutines: List<MyRoutine>) : MyRoutinesEvent()
    object SomethingWentWrong : MyRoutinesEvent()
}