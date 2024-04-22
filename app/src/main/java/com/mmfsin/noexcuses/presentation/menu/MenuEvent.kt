package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.domain.models.Routine

sealed class MenuEvent {
    object Completed : MenuEvent()
    class ActualRoutine(val routine: Routine?) : MenuEvent()
    object SWW : MenuEvent()
}