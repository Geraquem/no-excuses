package com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces

interface IMExerciseListener {
    fun onExerciseClick(chExerciseId: String)
    fun editExercise(chExerciseId: String)
//    fun moveExercisePosition(chExerciseId: String)
    fun onSeeExerciseButtonClick(id: String)

    fun deleteExerciseFromDay(chExerciseId: String)
    fun updateView()
}