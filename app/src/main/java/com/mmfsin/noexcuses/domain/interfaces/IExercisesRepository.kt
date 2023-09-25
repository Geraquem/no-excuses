package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IExercisesRepository {
    fun getMuscularGroups(): List<MuscularGroup>
    fun getExerciseByMuscularGroup(mGroup: String): List<Exercise>
}