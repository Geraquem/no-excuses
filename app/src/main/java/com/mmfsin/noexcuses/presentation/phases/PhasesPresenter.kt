package com.mmfsin.noexcuses.presentation.phases

import com.mmfsin.noexcuses.data.repository.PhasesRepository
import com.mmfsin.noexcuses.domain.models.Phase

class PhasesPresenter(val view: PhasesView) {

    private val repository by lazy { PhasesRepository() }

    fun getPhases() = view.getPhases(repository.getPhases())

    fun deletePhase(phase: Phase) {
        val result = repository.deletePhase(phase)
        if (result) view.phaseDeleted()
        else view.sww()
    }
}