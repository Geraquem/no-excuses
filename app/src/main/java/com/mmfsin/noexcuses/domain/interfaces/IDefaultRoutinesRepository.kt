package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.DefaultRoutine

interface IDefaultRoutinesRepository {
    fun getDefaultRoutines(): List<DefaultRoutine>
    fun getDefaultRoutineById(id: String): DefaultRoutine?
}