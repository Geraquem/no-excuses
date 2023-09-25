package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.ComboModel
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DayWithExercises
import io.realm.kotlin.where

class DaysRepository {

    private val realm by lazy { RealmDatabase() }

    fun getDays(phaseId: String): List<DayWithExercises> {
        val days = realm.getObjectsFromRealm {
            where<Day>().equalTo("phaseId", phaseId).findAll()
        }
        val result = mutableListOf<DayWithExercises>()
        days.forEach { day ->
            result.add(DayWithExercises(day, getExercisesForDays(day.id)))
        }
        return result
    }

    private fun getExercisesForDays(dayId: String): Int {
        return realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("dayId", dayId).findAll()
        }.count()
    }

    fun addDay(day: Day): Boolean = realm.addObject { day }

    fun deleteDay(day: Day) {
        val dayId = day.id
        deleteComboModels(dayId)
        realm.deleteObject({ day }, dayId)
    }

    private fun deleteComboModels(dayId: String) {
        val comboModels = realm.getObjectsFromRealm {
            where<ComboModel>().equalTo("dayId", dayId).findAll()
        }
        comboModels.forEach { cm -> realm.deleteObject({ cm }, cm.id) }
    }
}