package com.mmfsin.noexcuses.presentation.dfroutines.dfdays

import com.mmfsin.noexcuses.domain.models.DefaultRoutine

sealed class DefaultDaysDialogEvent {
    class GetDefaultRoutine(val routine: DefaultRoutine) : DefaultDaysDialogEvent()
//    class GetDefaultDays(val days: List<String>) : DefaultDaysDialogEvent()
    object SWW : DefaultDaysDialogEvent()
}