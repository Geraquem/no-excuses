package com.mmfsin.noexcuses.presentation.exercises.exercises.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.usecases.CheckExerciseFavUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateExerciseFavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val updateExerciseFavUseCase: UpdateExerciseFavUseCase,
    private val checkExerciseFavUseCase: CheckExerciseFavUseCase
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

    fun updateFav(id: String) {
        executeUseCase(
            { updateExerciseFavUseCase.execute(UpdateExerciseFavUseCase.Params(id)) },
            { checkIfExerciseFav(id) },
            { _event.value = ExerciseDialogEvent.SWW }
        )
    }

    private fun checkIfExerciseFav(id: String) {
        executeUseCase(
            { checkExerciseFavUseCase.execute(CheckExerciseFavUseCase.Params(id)) },
            { result -> _event.value = ExerciseDialogEvent.CheckFav(result) },
            { _event.value = ExerciseDialogEvent.SWW }
        )
    }
}