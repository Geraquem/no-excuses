package com.mmfsin.noexcuses.presentation.dayexercises.interfaces

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface IDayExercisesListener {
    fun onClick(exercise: RealmExercise)
    fun deleteDayExercise(exercise: RealmExercise)
}