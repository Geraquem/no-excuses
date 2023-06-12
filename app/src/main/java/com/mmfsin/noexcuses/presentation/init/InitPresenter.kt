package com.mmfsin.noexcuses.presentation.init

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.data.repository.FirebaseRepository
import com.mmfsin.noexcuses.domain.interfaces.IFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class InitPresenter(val view: InitView) : IFirebase, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val firebase by lazy { FirebaseRepository(this) }
    private val repository by lazy { ExercisesRepository() }

    fun checkWhereToCall() {
        /** check realm */
        launch(Dispatchers.IO) { firebase.getMuscularGroupsFromFirebase() }
    }

//    fun checkWhereToCall() {
//        val realmList = repository.getExercisesFromRealm()
//        if (realmList.isEmpty()) firebase.getExercisesFromFirebase()
//        else view.flowCompleted()
//    }

    override fun retrievedMGroupsFromFirebase(result: Boolean) {
        launch(Dispatchers.IO) { firebase.getExercisesFromFirebase() }
    }

    override fun retrievedExercisesFromFirebase(result: Boolean) {
        launch {
            if (result) view.flowCompleted()
            else view.sww()
        }
    }
}