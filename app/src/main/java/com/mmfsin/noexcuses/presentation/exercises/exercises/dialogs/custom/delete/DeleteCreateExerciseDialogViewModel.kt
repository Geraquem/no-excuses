package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.delete

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.DeleteCreatedExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteCreateExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val deleteCreatedExerciseUseCase: DeleteCreatedExerciseUseCase
) : BaseViewModel<DeleteCreateExerciseDialogEvent>() {

    fun getCreatedExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { DeleteCreateExerciseDialogEvent.CreatedExercise(it) }
                    ?: run { DeleteCreateExerciseDialogEvent.SWW }
            },
            { _event.value = DeleteCreateExerciseDialogEvent.SWW }
        )
    }

    fun deleteExercise(id: String) {
        executeUseCase(
            { deleteCreatedExerciseUseCase.execute(DeleteCreatedExerciseUseCase.Params(id)) },
            { _event.value = DeleteCreateExerciseDialogEvent.Deleted },
            { _event.value = DeleteCreateExerciseDialogEvent.SWW }
        )
    }
}