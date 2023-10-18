package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.interfaces

interface IAddChExerciseListener {
    fun addRepToSerie(id: String, reps: Int)
    fun addWeightToSerie(id: String, weight: Double)

    fun deleteSerie(id: String)
}