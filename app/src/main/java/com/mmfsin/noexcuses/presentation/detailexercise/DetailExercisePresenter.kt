package com.mmfsin.noexcuses.presentation.detailexercise

import com.mmfsin.noexcuses.data.repository.FirebaseRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercises
import com.mmfsin.noexcuses.domain.models.CompleteExercise
import com.mmfsin.noexcuses.domain.models.RealmExercise

class DetailExercisePresenter(val view: DetailExerciseView) : IExercises {

    private val repository by lazy { FirebaseRepository(this) }

    fun getExercise(exercise: RealmExercise) {
        repository.getExerciseFromFirebase(exercise)
    }

    override fun retrievedExercisesFromFirebase(result: Boolean) {}

    override fun retrievedSingleExercise(result: CompleteExercise?) {
        result?.let { exercise ->
            view.getExerciseDetail(exercise)
        } ?: run { view.sww() }
    }
}