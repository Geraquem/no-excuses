package com.mmfsin.noexcuses.presentation.myroutines.myroutines

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MyRoutinesEvent {
    class GetMyRoutines(val routines: List<Routine>) : MyRoutinesEvent()
    class PushPinUpdated(val routineId: String) : MyRoutinesEvent()
    object SWW : MyRoutinesEvent()
}