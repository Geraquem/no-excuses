package com.mmfsin.noexcuses.presentation.stretching

import com.mmfsin.noexcuses.domain.models.Stretch
import com.mmfsin.noexcuses.domain.models.Stretching

sealed class StretchingEvent {
    class GetStretchingData(val data: List<Stretch>) : StretchingEvent()
    class GetStretchingByMGroup(val stretching: List<Stretching>) : StretchingEvent()
    object SWW : StretchingEvent()
}