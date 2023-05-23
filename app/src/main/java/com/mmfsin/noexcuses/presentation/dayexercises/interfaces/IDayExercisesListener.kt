package com.mmfsin.noexcuses.presentation.dayexercises.interfaces

import com.mmfsin.noexcuses.domain.models.Exercise

interface IDayExercisesListener {
    fun onClick(exercise: Exercise)
    fun deleteDayExercise(exercise: Exercise)
}