package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.mmfsin.noexcuses.data.mappers.toDefaultRoutineList
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IRoutinesRepository {

    override suspend fun getPredeterminatedRoutines(): List<DefaultRoutine> {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<DefaultRoutineDTO>().findAll()
        }
        return exercises.toDefaultRoutineList()
    }
}