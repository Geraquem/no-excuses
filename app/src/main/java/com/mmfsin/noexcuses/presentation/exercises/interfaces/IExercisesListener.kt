package com.mmfsin.noexcuses.presentation.exercises.interfaces

import com.mmfsin.noexcuses.domain.models.Exercise

interface IExercisesListener {
    fun onClick(exercise: Exercise)
}