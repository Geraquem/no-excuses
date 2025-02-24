package com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises

sealed class CalendarDayExercisesEvent {
    class GetExercises(val exercises: List<Any>) : CalendarDayExercisesEvent()
    object SWW : CalendarDayExercisesEvent()
}