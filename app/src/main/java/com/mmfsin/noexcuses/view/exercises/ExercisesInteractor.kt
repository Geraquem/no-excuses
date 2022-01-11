package com.mmfsin.noexcuses.view.exercises

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.view.category.model.CategoryDTO

class ExercisesInteractor(private val listener: IExercises) {


    interface IExercises {
        fun ok(list: List<CategoryDTO>)
        fun ko()
    }
}