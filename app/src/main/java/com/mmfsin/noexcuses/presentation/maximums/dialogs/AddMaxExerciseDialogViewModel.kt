package com.mmfsin.noexcuses.presentation.maximums.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMaxExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase
) : BaseViewModel<AddMaxExercisesDialogEvent>() {

    fun getExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { AddMaxExercisesDialogEvent.GetExercise(it) }
                    ?: run { AddMaxExercisesDialogEvent.SWW }
            },
            { _event.value = AddMaxExercisesDialogEvent.SWW }
        )
    }
}