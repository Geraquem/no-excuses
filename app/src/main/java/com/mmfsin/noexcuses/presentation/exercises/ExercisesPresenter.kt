package com.mmfsin.noexcuses.presentation.exercises

import com.mmfsin.noexcuses.presentation.exercises.model.ExerciseDTO

class ExercisesPresenter(val view: ExercisesView) : ExercisesInteractor.IExercises {

    private val interactor by lazy { ExercisesInteractor(this) }

    fun getExercises(categoryName: String) {
        interactor.getExercises(categoryName)
    }

    override fun ok(list: List<ExerciseDTO>) {
        view.showExercises(list)
    }

    override fun ko() {
        view.somethingWentWrong()
    }
}