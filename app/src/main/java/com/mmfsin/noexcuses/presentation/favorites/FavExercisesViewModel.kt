package com.mmfsin.noexcuses.presentation.favorites

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetFavExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavExercisesViewModel @Inject constructor(
    private val getFavExercisesUseCase: GetFavExercisesUseCase
) : BaseViewModel<FavExercisesEvent>() {

    fun getFavExercises() {
        executeUseCase(
            { getFavExercisesUseCase.execute() },
            { result -> _event.value = FavExercisesEvent.GetFavExercises(result) },
            { _event.value = FavExercisesEvent.SWW }
        )
    }
}