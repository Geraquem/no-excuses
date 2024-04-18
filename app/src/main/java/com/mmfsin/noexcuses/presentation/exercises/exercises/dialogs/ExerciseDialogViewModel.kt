package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase
) : BaseViewModel<ExerciseDialogEvent>() {

    fun getExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { ExerciseDialogEvent.GetExercise(it) }
                    ?: run { ExerciseDialogEvent.SWW }
            },
            { _event.value = ExerciseDialogEvent.SWW }
        )
    }
}