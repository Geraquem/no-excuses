package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.models.CreatedExercise

interface IExercisesRepository {
    fun getMuscularGroups(): List<MuscularGroup>
    suspend fun getExercisesByMuscularGroup(mGroup: String): List<Exercise>
    fun getExerciseById(id: String): Exercise?

    fun getDayExercises(dayId: String): List<CompactExercise>
    fun getChExerciseById(chExerciseId: String): ChExercise?
    suspend fun addChExercise(chExercise: ChExercise)
    suspend fun editChExercise(chExercise: ChExercise)
    suspend fun moveChExercise(exercises: List<String>)
    suspend fun deleteChExercise(chExerciseId: String)

    fun getFavExercises(): List<Exercise>
    fun checkExerciseFav(exerciseId: String): Boolean
    suspend fun updateExerciseFav(exerciseId: String)

    fun createCustomExercise(createdExercise: CreatedExercise)
    suspend fun editCustomExercise(createdExercise: CreatedExercise, id: String)
    fun deleteCustomExercise(createdExerciseId: String)

    fun addDefaultExerciseAsMine(chExercise: ChExerciseDTO)
}