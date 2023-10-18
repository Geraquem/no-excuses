package com.mmfsin.noexcuses.presentation.routines

import com.mmfsin.noexcuses.domain.models.Routine

sealed class RoutinesEvent {
    class GetRoutines(val routines: List<Routine>) : RoutinesEvent()
    object SomethingWentWrong : RoutinesEvent()
}