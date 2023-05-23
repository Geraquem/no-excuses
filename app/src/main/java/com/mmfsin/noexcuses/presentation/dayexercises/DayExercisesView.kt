package com.mmfsin.noexcuses.presentation.dayexercises

import com.mmfsin.noexcuses.domain.models.Exercise

interface DayExercisesView {
    fun getDayExercises(exercises: List<Exercise>)
    fun dayExerciseDeleted()
    fun sww()
}