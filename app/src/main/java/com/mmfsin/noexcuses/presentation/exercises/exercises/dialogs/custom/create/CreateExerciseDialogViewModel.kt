package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs.custom.create

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CreateExerciseUseCase
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateExerciseDialogViewModel @Inject constructor(
    private val createExerciseUseCase: CreateExerciseUseCase
) : BaseViewModel<CreateExerciseDialogEvent>() {

    fun createExercise(exercise: CreatedExercise) {
        executeUseCase(
            { createExerciseUseCase.execute(CreateExerciseUseCase.Params(exercise)) },
            { _event.value = CreateExerciseDialogEvent.Created },
            { _event.value = CreateExerciseDialogEvent.SWW }
        )
    }
}