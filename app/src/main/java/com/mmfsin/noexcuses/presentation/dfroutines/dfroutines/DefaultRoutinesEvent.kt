package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

import com.mmfsin.noexcuses.domain.models.Routine

sealed class DefaultRoutinesEvent {
    class GetDefaultRoutines(val defaultRoutines: List<Routine>) : DefaultRoutinesEvent()
    object PushPinUpdated : DefaultRoutinesEvent()
    object RoutineAddedToMine : DefaultRoutinesEvent()
    object SWW : DefaultRoutinesEvent()
}