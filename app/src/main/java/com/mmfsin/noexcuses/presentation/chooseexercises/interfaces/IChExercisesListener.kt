package com.mmfsin.noexcuses.presentation.chooseexercises.interfaces

import com.mmfsin.noexcuses.domain.models.Exercise

interface IChExercisesListener {
    fun onClick(exercise: Exercise)
}