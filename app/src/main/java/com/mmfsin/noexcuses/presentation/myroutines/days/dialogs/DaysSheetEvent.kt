package com.mmfsin.noexcuses.presentation.myroutines.days.dialogs

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.MyRoutine

sealed class DaysSheetEvent {
    class GetRoutine(val routine: MyRoutine) : DaysSheetEvent()
    class GetDays(val days: List<Day>) : DaysSheetEvent()
    object SWW : DaysSheetEvent()
}