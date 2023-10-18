package com.mmfsin.noexcuses.presentation.myroutines.days.fragments

import com.mmfsin.noexcuses.domain.models.Day

sealed class DayConfigEvent {
    object FlowCompleted : DayConfigEvent()
    class GetDay(val day: Day) : DayConfigEvent()
    object SomethingWentWrong : DayConfigEvent()
}