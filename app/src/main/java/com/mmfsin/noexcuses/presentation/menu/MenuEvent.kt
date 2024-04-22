package com.mmfsin.noexcuses.presentation.menu

sealed class MenuEvent {
    object Completed : MenuEvent()
    class ActualRoutine(val routine: Any?) : MenuEvent()
    object SWW : MenuEvent()
}