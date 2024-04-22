package com.mmfsin.noexcuses.presentation.myroutines.mdays

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine

sealed class DaysDialogEvent {
    class GetRoutine(val routine: Routine) : DaysDialogEvent()
    class GetDays(val days: List<Day>) : DaysDialogEvent()
    object SWW : DaysDialogEvent()
}