package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toMuscularGroupList
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import io.realm.kotlin.where
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IExercisesRepository {

    override suspend fun getMuscularGroups(): List<MuscularGroup> {
        val groups = realmDatabase.getObjectsFromRealm { where<MuscularGroupDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.sortedBy { it.order }
            .toMuscularGroupList() else emptyList()
    }

    override suspend fun getExerciseByMuscularGroup(mGroup: String) {

    }
}