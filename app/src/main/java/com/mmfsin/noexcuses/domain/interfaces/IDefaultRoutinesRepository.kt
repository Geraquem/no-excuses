package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.DefaultRoutine

interface IDefaultRoutinesRepository {
    fun getDefaultRoutines(): List<DefaultRoutine>
    fun getDefaultRoutineById(id: String): DefaultRoutine?

    fun getDefaultDays(routineId: String): List<Day>
    fun getDefaultDayById(id: String): Day?

    fun getDefaultExercises(dayId: String): List<DefaultExercise>
    fun getDefaultExerciseById(id: String): DefaultExercise?
}