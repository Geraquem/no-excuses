package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IExercisesRepository {
    suspend fun getMuscularGroups(): List<MuscularGroup>
    suspend fun getExerciseByMuscularGroup(mGroup: String)
}