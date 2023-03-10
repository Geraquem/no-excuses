package com.mmfsin.noexcuses.presentation.routines

import com.mmfsin.noexcuses.data.repository.PhasesRepository
import com.mmfsin.noexcuses.presentation.routines.RoutinesView

class RoutinesPresenter(val view: RoutinesView) {

    private val repository by lazy { PhasesRepository() }

//    fun getRoutines() = view.getRoutines(repository.getPhases())

}