package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Routine

interface IDefaultRoutinesRepository {
    suspend fun getDefaultRoutines(): List<Routine>
    fun getDefaultRoutineById(id: String): Routine?
    fun updateRoutinePushPin(id: String)

    suspend fun getDefaultDays(routineId: String): List<Day>
    fun getDefaultDayById(id: String): Day?

    suspend fun getDefaultExercises(routineId: String, dayId: String): List<DefaultExercise>
    fun getDefaultExerciseById(id: String): DefaultExercise?
}