package com.mmfsin.noexcuses.presentation.exercises.interfaces

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface IExercisesListener {
    fun onClick(exercise: RealmExercise)
}