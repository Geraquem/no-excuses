package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import io.realm.kotlin.where

class MGroupsRepository {

    private val realm by lazy { RealmDatabase() }

    fun getMuscularGroupsFromRealm(): List<MuscularGroup> = realm.getObjectsFromRealm {
        where<MuscularGroup>().findAll()
    }
}

