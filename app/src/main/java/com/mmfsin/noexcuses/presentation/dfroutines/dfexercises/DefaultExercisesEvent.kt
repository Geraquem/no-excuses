package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise

sealed class DefaultExercisesEvent {
    class GetDefaultDay(val day: Day) : DefaultExercisesEvent()
    class GetDefaultDayExercises(val exercises: List<DefaultExercise>) : DefaultExercisesEvent()
    object ExercisesRegisteredInCalendar : DefaultExercisesEvent()
    object SWW : DefaultExercisesEvent()
}