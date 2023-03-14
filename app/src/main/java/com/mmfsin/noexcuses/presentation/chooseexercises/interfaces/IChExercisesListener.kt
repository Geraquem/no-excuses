package com.mmfsin.noexcuses.presentation.chooseexercises.interfaces

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface IChExercisesListener {
    fun onClick(exercise: RealmExercise)
}