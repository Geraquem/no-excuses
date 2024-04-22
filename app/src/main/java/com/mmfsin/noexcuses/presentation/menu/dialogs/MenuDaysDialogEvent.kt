package com.mmfsin.noexcuses.presentation.menu.dialogs

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine

sealed class MenuDaysDialogEvent {
    class GetRoutine(val routine: Routine) : MenuDaysDialogEvent()
    class GetDays(val days: List<Day>) : MenuDaysDialogEvent()
    object SWW : MenuDaysDialogEvent()
}