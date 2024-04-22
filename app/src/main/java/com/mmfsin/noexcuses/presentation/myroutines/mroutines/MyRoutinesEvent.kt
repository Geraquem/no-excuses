package com.mmfsin.noexcuses.presentation.myroutines.mroutines

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MyRoutinesEvent {
    class IsFistTime(val firstTime: Boolean) : MyRoutinesEvent()
    class GetMyRoutines(val routines: List<Routine>) : MyRoutinesEvent()
    class PushPinUpdated(val myRoutineId: String) : MyRoutinesEvent()
    object SWW : MyRoutinesEvent()
}