package com.mmfsin.noexcuses.presentation.phases.interfaces

import com.mmfsin.noexcuses.domain.models.Phase

interface IConfigPhasesListener {
    fun edit(phase: Phase)
    fun delete(phase: Phase)
}