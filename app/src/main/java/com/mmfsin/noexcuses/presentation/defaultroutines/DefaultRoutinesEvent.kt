package com.mmfsin.noexcuses.presentation.defaultroutines

import com.mmfsin.noexcuses.domain.models.DefaultRoutine

sealed class DefaultRoutinesEvent {
    class GetDefaultRoutines(val defaultRoutines: List<DefaultRoutine>) : DefaultRoutinesEvent()
    object SomethingWentWrong : DefaultRoutinesEvent()
}