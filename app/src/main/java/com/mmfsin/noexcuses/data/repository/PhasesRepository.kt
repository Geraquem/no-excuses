package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.ComboModel
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Phase
import io.realm.kotlin.where

class PhasesRepository {

    private val realm by lazy { RealmDatabase() }

    fun getPhases(): List<Phase> {
        return realm.getObjectsFromRealm { where<Phase>().findAll() }
    }

    fun addPhase(phase: Phase): Boolean = realm.addObject { phase }

    fun deletePhase(phase: Phase): Boolean {
        val phaseId = phase.id
        return try {
            deleteDaysFromPhase(phaseId)
            realm.deleteObject({ phase }, phaseId)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun deleteDaysFromPhase(phaseId: String) {
        val days = realm.getObjectsFromRealm {
            where<Day>().equalTo("phaseId", phaseId).findAll()
        }
        days.forEach { day -> deleteComboModels(day.id) }
        days.forEach { day -> realm.deleteObject({ day }, day.id) }
    }

    private fun deleteComboModels(dayId: String) {
        val comboModels = realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("dayId", dayId).findAll()
        }
        comboModels.forEach { cm -> realm.deleteObject({ cm }, cm.id) }
    }
}