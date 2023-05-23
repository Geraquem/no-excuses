package com.mmfsin.noexcuses.presentation.init

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.data.repository.FirebaseRepository
import com.mmfsin.noexcuses.domain.interfaces.IFirebase

class InitPresenter(val view: InitView) : IFirebase {

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

    override fun retrievedMGroupsFromFirebase(result: Boolean) {

    }

    override fun retrievedExercisesFromFirebase(result: Boolean) {
        if (result) view.flowCompleted()
        else view.sww()
    }
}