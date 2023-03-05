package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.Phase
import io.realm.kotlin.where

class PhasesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getPhases(): List<Phase> {
        return realm.getObjectsFromRealm {
            where<Phase>().findAll()
        }
    }

    fun addPhase(phase: Phase): Boolean = realm.addObject { phase }

    fun deletePhase(phase: Phase): Boolean = realm.deleteObject({ phase }, phase.id)
}