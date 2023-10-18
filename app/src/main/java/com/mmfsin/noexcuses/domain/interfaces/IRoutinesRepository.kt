package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Routine

interface IRoutinesRepository {
    fun getRoutines(): List<Routine>
}