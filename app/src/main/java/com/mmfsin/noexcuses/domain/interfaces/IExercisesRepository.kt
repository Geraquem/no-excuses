package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IExercisesRepository {
    fun getMuscularGroups(): List<MuscularGroup>
    fun getExercisesByMuscularGroup(mGroup: String): List<Exercise>
    fun getExerciseById(id: String): Exercise?

    fun getDayExercises(dayId: String)
}