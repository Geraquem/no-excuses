package com.mmfsin.noexcuses.presentation.myroutines.mexercises.actual

import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Day

sealed class ActualExercisesFromMenuEvent {
    class GetDay(val day: Day) : ActualExercisesFromMenuEvent()
    class GetDayExercises(val exercises: List<CompactExercise>) : ActualExercisesFromMenuEvent()
    object SWW : ActualExercisesFromMenuEvent()
}