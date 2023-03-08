package com.mmfsin.noexcuses.presentation.init

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.data.repository.PhasesRepository
import com.mmfsin.noexcuses.presentation.routines.RoutinesView

class InitPresenter(val view: InitView) {

    private val repository by lazy { ExercisesRepository() }

    fun checkWhereToCall() {
        val realmList = repository.getExercisesFromRealm()
        if (realmList.isEmpty()) {
            getExercisesFromFirebase()
        } else {
            view.onFirebaseResult()
        }
    }

    fun getExercisesFromFirebase() = repository.getExercisesFromFirebase()
}