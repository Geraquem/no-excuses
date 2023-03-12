package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Phase
import io.realm.kotlin.where

class DaysRepository {

    private val realm by lazy { RealmDatabase() }

    fun getDays(): List<Day> {
        return realm.getObjectsFromRealm {
            where<Day>().findAll()
        }
    }

    fun addDay(day: Day): Boolean = realm.addObject { day }

    fun deleteDay(day: Day): Boolean = realm.deleteObject({ day }, day.id)
}