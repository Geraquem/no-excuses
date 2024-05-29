package com.mmfsin.noexcuses.presentation.stretching

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Stretching

sealed class StretchingEvent {
    class GetMuscularGroups(val mGroups: List<MuscularGroup>) : StretchingEvent()
    class GetStretching(val stretching: List<Stretching>) : StretchingEvent()
    object SWW : StretchingEvent()
}