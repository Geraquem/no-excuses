package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.ComboModel
import com.mmfsin.noexcuses.domain.models.RealmExercise
import io.realm.kotlin.where

class ExercisesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromRealm(): List<RealmExercise> {
        return realm.getObjectsFromRealm {
            where<RealmExercise>().findAll()
        }
    }

    fun getDayExercises(dayId: String): List<RealmExercise> {
        val list = mutableListOf<RealmExercise>()
        val exercises = realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("dayId", dayId).findAll()
        }

        for (exercise in exercises) {
            val o = realm.getObjectsFromRealm {
                where<RealmExercise>().equalTo("id", exercise.exerciseId).findAll()
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