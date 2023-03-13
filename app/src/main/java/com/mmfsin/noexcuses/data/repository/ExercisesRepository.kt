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

    fun saveCombo(comboModel: ComboModel): Boolean = realm.addObject { comboModel }

    fun deleteExercise(realmExercise: RealmExercise): Boolean = realm.deleteObject({ realmExercise }, realmExercise.id)
}