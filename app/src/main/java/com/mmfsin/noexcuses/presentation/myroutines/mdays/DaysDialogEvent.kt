package com.mmfsin.noexcuses.presentation.myroutines.mdays

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.MyRoutine

sealed class DaysDialogEvent {
    class GetRoutine(val routine: MyRoutine) : DaysDialogEvent()
    class GetDays(val days: List<Day>) : DaysDialogEvent()
    object SWW : DaysDialogEvent()
}