package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toRoutineList
import com.mmfsin.noexcuses.data.models.RoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import io.realm.kotlin.where
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IRoutinesRepository {

    override fun getRoutines(): List<Routine> {
        val routines = realmDatabase.getObjectsFromRealm { where<RoutineDTO>().findAll() }
        return if (routines.isNotEmpty()) routines.toRoutineList()
        else emptyList()
    }
}