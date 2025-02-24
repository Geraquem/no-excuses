package com.mmfsin.noexcuses.presentation.menu.actual

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.MExercisesEvent

sealed class ActualExercisesFromMenuEvent {
    class GetDay(val day: Day) : ActualExercisesFromMenuEvent()
    class GetDayExercises(val exercises: List<Any>) : ActualExercisesFromMenuEvent()
    object ExercisesRegisteredInCalendar : ActualExercisesFromMenuEvent()
    object SWW : ActualExercisesFromMenuEvent()
}