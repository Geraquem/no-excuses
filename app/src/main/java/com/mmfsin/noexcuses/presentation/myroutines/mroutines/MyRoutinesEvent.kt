package com.mmfsin.noexcuses.presentation.myroutines.mroutines

import com.mmfsin.noexcuses.domain.models.MyRoutine

sealed class MyRoutinesEvent {
    class IsFistTime(val firstTime: Boolean) : MyRoutinesEvent()
    class GetMyRoutines(val myRoutines: List<MyRoutine>) : MyRoutinesEvent()
    class PushPinUpdated(val myRoutineId: String) : MyRoutinesEvent()
    object SWW : MyRoutinesEvent()
}