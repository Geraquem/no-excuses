package com.mmfsin.noexcuses.presentation.phases

import com.mmfsin.noexcuses.domain.models.Phase

interface PhasesView {
    fun getPhases(phases: List<Phase>)
    fun sww()
}