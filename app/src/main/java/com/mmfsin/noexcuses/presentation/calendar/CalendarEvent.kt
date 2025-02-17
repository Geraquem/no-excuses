package com.mmfsin.noexcuses.presentation.calendar

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine

sealed class CalendarEvent {
    object SWW : CalendarEvent()
}