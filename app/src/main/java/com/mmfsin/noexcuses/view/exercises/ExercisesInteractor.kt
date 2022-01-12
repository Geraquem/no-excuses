package com.mmfsin.noexcuses.view.exercises

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.view.exercises.model.ExerciseDTO

class ExercisesInteractor(private val listener: IExercises) {

    fun getExercises(categoryName: String) {
        val list = mutableListOf<ExerciseDTO>()
        Firebase.database.reference.child("exercises").child(categoryName)
            .get().addOnSuccessListener {
                for (child in it.children) {
                    child.getValue(ExerciseDTO::class.java)?.let { exercise -> list.add(exercise) }
                }
                listener.ok(list)

            }.addOnFailureListener { listener.ko() }

    }

    interface IExercises {
        fun ok(list: List<ExerciseDTO>)
        fun ko()
    }
}