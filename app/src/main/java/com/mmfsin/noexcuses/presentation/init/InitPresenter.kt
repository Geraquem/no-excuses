package com.mmfsin.noexcuses.presentation.init

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.data.repository.FirebaseRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercises

class InitPresenter(val view: InitView) : IExercises {

    private val firebase by lazy { FirebaseRepository(this) }
    private val repository by lazy { ExercisesRepository() }

    fun checkWhereToCall() {
         firebase.getExercisesFromFirebase()
    }

//    fun checkWhereToCall() {
//        val realmList = repository.getExercisesFromRealm()
//        if (realmList.isEmpty()) firebase.getExercisesFromFirebase()
//        else view.flowCompleted()
//    }

    override fun retrievedFromFirebase(result: Boolean) {
        if (result) view.flowCompleted()
        else view.sww()
    }
}