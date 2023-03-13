package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.CompleteExercise

interface IExercises {
    fun retrievedExercisesFromFirebase(result: Boolean)
    fun retrievedSingleExercise(result: CompleteExercise?)
}