package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine

sealed class DefaultDaysDialogEvent {
    class GetDefaultRoutine(val routine: Routine) : DefaultDaysDialogEvent()
    class GetDefaultDays(val days: List<Day>) : DefaultDaysDialogEvent()
    object SWW : DefaultDaysDialogEvent()
}