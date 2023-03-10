package com.mmfsin.noexcuses.presentation.phases.dialogs.newphase

import com.mmfsin.noexcuses.data.repository.PhasesRepository
import com.mmfsin.noexcuses.domain.models.Phase
import java.util.*

class NewPhasePresenter(val view: NewPhaseView) {

    private val repository by lazy { PhasesRepository() }

    fun save(id: String?, name: String, description: String) {
         val finalId = id ?: run { UUID.randomUUID().toString() }

        val phase = if (description.isNotEmpty()) Phase(finalId, name, description)
        else Phase(finalId, name)

        val result = repository.addPhase(phase)
        if (result) view.savedInRealm() else view.sww()
    }
}