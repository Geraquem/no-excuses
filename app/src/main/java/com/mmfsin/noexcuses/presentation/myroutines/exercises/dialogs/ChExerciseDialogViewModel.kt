package com.mmfsin.noexcuses.presentation.myroutines.exercises.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.*
import com.mmfsin.noexcuses.presentation.models.DataChExercise
import com.mmfsin.noexcuses.presentation.models.IdGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChExerciseDialogViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val addChExerciseUseCase: AddChExerciseUseCase,
    private val getChExerciseUseCase: GetChExerciseUseCase,
    private val editChExerciseUseCase: EditChExerciseUseCase,
    private val deleteChExerciseUseCase: DeleteChExerciseUseCase
) : BaseViewModel<ChExerciseDialogEvent>() {

    fun getExercise(id: String) {
        executeUseCase(
            { getExerciseUseCase.execute(GetExerciseUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { ChExerciseDialogEvent.GetExercise(it) }
                    ?: run { ChExerciseDialogEvent.SWW }
            },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }

    fun getDay(id: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { ChExerciseDialogEvent.GetDay(it) }
                    ?: run { ChExerciseDialogEvent.SWW }
            },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }

    fun addChExercise(idGroup: IdGroup, dataChExercise: DataChExercise) {
        executeUseCase(
            { addChExerciseUseCase.execute(AddChExerciseUseCase.Params(idGroup, dataChExercise)) },
            { _event.value = ChExerciseDialogEvent.FlowCompleted },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }

    fun editChExercise(chExerciseId: String, dataChExercise: DataChExercise) {
        executeUseCase(
            {
                editChExerciseUseCase.execute(
                    EditChExerciseUseCase.Params(chExerciseId, dataChExercise)
                )
            },
            { _event.value = ChExerciseDialogEvent.FlowCompleted },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }

    fun getChExercise(chExerciseId: String) {
        executeUseCase(
            { getChExerciseUseCase.execute(GetChExerciseUseCase.Params(chExerciseId)) },
            { result ->
                _event.value = result?.let { ChExerciseDialogEvent.GetChExercise(it) }
                    ?: run { ChExerciseDialogEvent.SWW }
            },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }

    fun deleteChExercise(chExerciseId: String) {
        executeUseCase(
            { deleteChExerciseUseCase.execute(DeleteChExerciseUseCase.Params(chExerciseId)) },
            { _event.value = ChExerciseDialogEvent.FlowCompleted },
            { _event.value = ChExerciseDialogEvent.SWW }
        )
    }
}