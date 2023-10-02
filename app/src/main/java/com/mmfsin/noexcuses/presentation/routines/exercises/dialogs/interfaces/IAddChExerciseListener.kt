package com.mmfsin.noexcuses.presentation.routines.exercises.dialogs.interfaces

interface IAddChExerciseListener {
    fun addRepToSerie(id: String, reps: Int)
    fun addWeightToSerie(id: String, weight: Double)
}