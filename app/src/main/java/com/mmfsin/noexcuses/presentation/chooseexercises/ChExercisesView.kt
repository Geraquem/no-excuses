package com.mmfsin.noexcuses.presentation.chooseexercises

import com.mmfsin.noexcuses.domain.models.Exercise

interface ChExercisesView {
    fun getExercises(exercises: List<Exercise>)
    fun savedExerciseInDay(result: Boolean)
    fun sww()
}