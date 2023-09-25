package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toExerciseList
import com.mmfsin.noexcuses.data.mappers.toMuscularGroupList
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.utils.CATEGORY
import io.realm.kotlin.where
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IExercisesRepository {

    override fun getMuscularGroups(): List<MuscularGroup> {
        val groups = realmDatabase.getObjectsFromRealm { where<MuscularGroupDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.sortedBy { it.order }.toMuscularGroupList()
        else emptyList()
    }

    override fun getExerciseByMuscularGroup(mGroup: String): List<Exercise> {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ExerciseDTO>().equalTo(CATEGORY, mGroup).findAll()
        }
        return if (exercises.isNotEmpty()) exercises.sortedBy { it.order }.toExerciseList()
        else emptyList()
    }
}