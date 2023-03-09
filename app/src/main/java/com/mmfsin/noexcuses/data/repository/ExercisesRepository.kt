package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.Exercise
import io.realm.kotlin.where

class ExercisesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromRealm(): List<Exercise> {
        return realm.getObjectsFromRealm {
            where<Exercise>().findAll()
        }
    }

    fun saveExercise(exercise: Exercise): Boolean = realm.addObject { exercise }

    fun deleteExercise(exercise: Exercise): Boolean = realm.deleteObject({ exercise }, exercise.id)
}