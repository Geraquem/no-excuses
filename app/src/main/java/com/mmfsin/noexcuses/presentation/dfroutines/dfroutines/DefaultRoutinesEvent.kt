package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines

import com.mmfsin.noexcuses.domain.models.DefaultRoutine

sealed class DefaultRoutinesEvent {
    class GetDefaultRoutines(val defaultRoutines: List<DefaultRoutine>) : DefaultRoutinesEvent()
    object SWW : DefaultRoutinesEvent()
}