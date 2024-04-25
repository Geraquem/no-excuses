package com.mmfsin.noexcuses.presentation.menu.actual

import com.mmfsin.noexcuses.domain.models.Day

sealed class ActualExercisesFromMenuEvent {
    class GetDay(val day: Day) : ActualExercisesFromMenuEvent()
    class GetDayExercises(val exercises: List<Any>) : ActualExercisesFromMenuEvent()
    object SWW : ActualExercisesFromMenuEvent()
}