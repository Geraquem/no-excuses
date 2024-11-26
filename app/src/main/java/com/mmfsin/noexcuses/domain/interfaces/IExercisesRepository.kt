package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IExercisesRepository {
    fun getMuscularGroups(): List<MuscularGroup>
    suspend fun getExercisesByMuscularGroup(mGroup: String): List<Exercise>
    fun getExerciseById(id: String): Exercise?

    fun getDayExercises(dayId: String): List<CompactExercise>
    fun getChExerciseById(chExerciseId: String): ChExercise?
    fun addChExercise(chExercise: ChExercise)
    fun editChExercise(chExercise: ChExercise)
    fun deleteChExercise(chExerciseId: String)

    fun getFavExercises(): List<Exercise>
    fun checkExerciseFav(exerciseId: String): Boolean
    fun updateExerciseFav(exerciseId: String)

    fun deleteExercisesFromDeletedDay(dayId: String)
    fun deleteExercisesFromDeletedRoutine(routineId: String)
}