package com.mmfsin.noexcuses.presentation.myroutines.days.interfaces

interface IDayExerciseListener {
    fun onExerciseClick(chExerciseId: String)
    fun onExerciseLongClick(chExerciseId: String)

    fun deleteExerciseFromDay(chExerciseId: String)
    fun updateView()
}