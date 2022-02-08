package com.mmfsin.noexcuses.intefaces

import com.mmfsin.noexcuses.view.category.model.CategoryDTO
import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO

interface IFragment {
    fun openExercises(category: CategoryDTO)
    fun openExerciseDetail(exercise: ExerciseDTO)
    fun close()
}