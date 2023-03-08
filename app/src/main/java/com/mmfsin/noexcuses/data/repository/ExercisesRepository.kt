package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.Exercise
import io.realm.kotlin.where

class ExercisesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromFirebase() {
        Firebase.database.reference.child("ejercicios").get().addOnSuccessListener {
            for (muscularName in it.children) {
                for (exercise in muscularName.children) {
                    exercise.getValue(Exercise::class.java)?.let { ex -> saveExercise(ex) }
                }
            }
        }.addOnFailureListener {
            val a = 2
        }

    }

    fun getExercisesFromRealm(): List<Exercise> {
        return realm.getObjectsFromRealm {
            where<Exercise>().findAll()
        }
    }

    fun saveExercise(exercise: Exercise): Boolean = realm.addObject { exercise }

    fun deleteExercise(exercise: Exercise): Boolean = realm.deleteObject({ exercise }, exercise.id)
}