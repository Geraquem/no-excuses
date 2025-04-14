package com.mmfsin.noexcuses.presentation.maximums.dialogs.add

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.RegisterMaximumDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMaxExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val registerMaximumDataUseCase: RegisterMaximumDataUseCase
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

    fun saveMaximumData(data: TempMaximumData) {
        executeUseCase(
            { registerMaximumDataUseCase.execute(RegisterMaximumDataUseCase.Params(data)) },
            { _event.value = AddMaxExercisesDialogEvent.Registered },
            { _event.value = AddMaxExercisesDialogEvent.SWW }
        )
    }
}