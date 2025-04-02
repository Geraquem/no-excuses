package com.mmfsin.noexcuses.presentation.exercises.exercises

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : BaseViewModel<ExercisesEvent>() {

    fun getExercises(mGroup: String, newCreated: Boolean = false) {
        executeUseCase(
            { getExercisesUseCase.execute(GetExercisesUseCase.Params(mGroup)) },
            { result ->
                _event.value =
                    if (result.isNotEmpty()) ExercisesEvent.GetExercises(result, newCreated)
                    else ExercisesEvent.SWW
            },
            { _event.value = ExercisesEvent.SWW }
        )
    }
}