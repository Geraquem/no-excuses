package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.DefaultRoutine

interface IRoutinesRepository {
    suspend fun getPredeterminatedRoutines(): List<DefaultRoutine>
}