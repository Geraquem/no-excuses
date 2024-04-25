package com.mmfsin.noexcuses.presentation.myroutines.myroutines

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MyRoutinesEvent {
    class IsFistTime(val firstTime: Boolean) : MyRoutinesEvent()
    class GetMyRoutines(val routines: List<Routine>) : MyRoutinesEvent()
    object PushPinUpdated : MyRoutinesEvent()
    object SWW : MyRoutinesEvent()
}