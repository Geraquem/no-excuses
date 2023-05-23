package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.ComboModel
import com.mmfsin.noexcuses.domain.models.Exercise
import io.realm.kotlin.where

class ExercisesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromRealm(): List<Exercise> {
        return realm.getObjectsFromRealm {
            where<Exercise>().findAll()
        }
    }

    fun getDayExercises(dayId: String): List<Exercise> {
        val list = mutableListOf<Exercise>()
        val exercises = realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("dayId", dayId).findAll()
        }

        for (exercise in exercises) {
            val o = realm.getObjectsFromRealm {
                where<Exercise>().equalTo("id", exercise.exerciseId).findAll()
            }[0]
            list.add(o)
        }

        return list
    }

    fun getComModelByExerciseId(exerciseId: String): ComboModel {
        return realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("exerciseId", exerciseId).findAll()
        }[0]
    }

    fun saveComboModel(comboModel: ComboModel): Boolean = realm.addObject { comboModel }

    fun deleteComboModel(comboModel: ComboModel): Boolean =
        realm.deleteObject({ comboModel }, comboModel.id)
}