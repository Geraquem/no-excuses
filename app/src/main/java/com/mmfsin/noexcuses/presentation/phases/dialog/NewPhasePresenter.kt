package com.mmfsin.noexcuses.presentation.phases.dialog

import com.mmfsin.noexcuses.data.repository.PhasesRepository
import com.mmfsin.noexcuses.domain.models.Phase
import java.util.*

class NewPhasePresenter(val view: NewPhaseView) {

    private val repository by lazy { PhasesRepository() }

    fun save(name: String, description: String) {
        val id = UUID.randomUUID().toString()

        val phase = if (description.isNotEmpty()) Phase(id, name, description)
        else Phase(id, name)

        val result = repository.addPhase(phase)
        if (result) view.savedInRealm() else view.sww()
    }
}