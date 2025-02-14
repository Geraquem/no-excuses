package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs.interfaces

interface IAddChExerciseListener {
    fun addRepToSerie(pos: String, reps: Int)
    fun addWeightToSerie(pos: String, weight: Double)

    fun deleteSerie(id: String)
}