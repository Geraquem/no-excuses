package com.mmfsin.noexcuses.presentation.myroutines.days

import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Day

sealed class DaysEvent {
    class GetDays(val days: List<Day>) : DaysEvent()
    class GetDay(val day: Day) : DaysEvent()
    class GetDayExercises(val exercises: List<CompactExercise>) : DaysEvent()
    object SomethingWentWrong : DaysEvent()
}