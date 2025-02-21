package com.mmfsin.noexcuses.presentation.calendar.dialogs.exercises

sealed class CalendarDayExercisesEvent {
    class GetExercises(val exercises: String) : CalendarDayExercisesEvent()
    object SWW : CalendarDayExercisesEvent()
}