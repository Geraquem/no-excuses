package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.edit

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.EditCreatedExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCreatedExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val editCreatedExerciseUseCase: EditCreatedExerciseUseCase
) : BaseViewModel<EditCreateExerciseDialogEvent>() {

    fun getCreatedExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { EditCreateExerciseDialogEvent.CreatedExercise(it) }
                    ?: run { EditCreateExerciseDialogEvent.SWW }
            },
            { _event.value = EditCreateExerciseDialogEvent.SWW }
        )
    }

    fun editExercise(exercise: CreatedExercise, id: String) {
        executeUseCase(
            { editCreatedExerciseUseCase.execute(EditCreatedExerciseUseCase.Params(exercise, id)) },
            { _event.value = EditCreateExerciseDialogEvent.Edited },
            { _event.value = EditCreateExerciseDialogEvent.SWW }
        )
    }
}