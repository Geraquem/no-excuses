package com.mmfsin.noexcuses.presentation.phases

import com.mmfsin.noexcuses.data.repository.PhasesRepository

class PhasesPresenter(val view: PhasesView) {

    private val repository by lazy { PhasesRepository() }

    fun getPhases() = view.getPhases(repository.getPhases())

}