package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Routine

interface IDefaultRoutinesRepository {
    fun getDefaultRoutines(): List<Routine>
    fun getDefaultRoutineById(id: String): Routine?
    fun updateRoutinePushPin(id: String)

    fun getDefaultDays(routineId: String): List<Day>
    fun getDefaultDayById(id: String): Day?

    fun getDefaultExercises(dayId: String): List<DefaultExercise>
    fun getDefaultExerciseById(id: String): DefaultExercise?
}