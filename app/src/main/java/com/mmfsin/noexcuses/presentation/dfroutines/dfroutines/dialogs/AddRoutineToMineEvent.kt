package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.dialogs

sealed class AddRoutineToMineEvent {
    object RoutineAddedToMine : AddRoutineToMineEvent()
    object SWW : AddRoutineToMineEvent()
}