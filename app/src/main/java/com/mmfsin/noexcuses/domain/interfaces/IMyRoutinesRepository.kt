package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.MyRoutine

interface IMyRoutinesRepository {
    fun getRoutines(): List<MyRoutine>
    fun getRoutineById(id: String): MyRoutine?
    fun updateRoutinePushPin(id: String)
    fun addRoutine(title: String, description: String?)
    fun editRoutine(id: String, title: String, description: String?)
    fun deleteRoutine(id: String)

    fun getRoutineDays(routineId: String): List<Day>
    fun getDayById(dayId: String): Day?
    fun addDay(routineId: String, title: String)
    fun editDay(id: String, title: String)
    fun deleteDay(id: String)
}