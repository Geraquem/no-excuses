package com.mmfsin.noexcuses.presentation.myroutines.exercises.interfaces

interface IChExercisesListener {
    fun onExerciseClick(id: String)
    fun seeExercise(id: String)

    fun showSnackBar()
}