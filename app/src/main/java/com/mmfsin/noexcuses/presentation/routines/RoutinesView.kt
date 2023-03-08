package com.mmfsin.noexcuses.presentation.routines

import com.mmfsin.noexcuses.domain.models.Phase

interface RoutinesView {
    fun getRoutines(phases: List<Phase>)
    fun sww()
}