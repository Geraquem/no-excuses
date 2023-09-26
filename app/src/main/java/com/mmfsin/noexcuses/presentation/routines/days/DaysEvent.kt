package com.mmfsin.noexcuses.presentation.routines.days

import com.mmfsin.noexcuses.domain.models.Day

sealed class DaysEvent {
    class GetDays(val days: List<Day>) : DaysEvent()
    class GetDay(val day: Day) : DaysEvent()
    class GetDayExercises() : DaysEvent()
    object SomethingWentWrong : DaysEvent()
}