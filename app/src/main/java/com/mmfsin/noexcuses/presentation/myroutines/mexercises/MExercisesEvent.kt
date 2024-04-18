package com.mmfsin.noexcuses.presentation.myroutines.mexercises

import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Day

sealed class MExercisesEvent {
    class GetDay(val day: Day) : MExercisesEvent()
    class GetDayExercises(val exercises: List<CompactExercise>) : MExercisesEvent()
    object SWW : MExercisesEvent()
}