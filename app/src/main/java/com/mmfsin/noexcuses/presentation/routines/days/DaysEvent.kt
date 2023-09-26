package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.domain.models.Day

sealed class DaysEvent {
    class GetDays(val days: List<Day>) : DaysEvent()
    object SomethingWentWrong : DaysEvent()
}