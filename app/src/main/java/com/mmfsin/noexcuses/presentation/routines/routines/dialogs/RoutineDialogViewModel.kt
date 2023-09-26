package com.mmfsin.noexcuses.presentation.routines.routines.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.EditRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutineByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoutineDialogViewModel @Inject constructor(
    private val addRoutineUseCase: AddRoutineUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : BaseViewModel<RoutineDialogEvent>() {

    fun addRoutine(title: String, description: String) {
        executeUseCase(
            { addRoutineUseCase.execute(AddRoutineUseCase.Params(title, description)) },
            { _event.value = RoutineDialogEvent.FlowCompleted },
            { _event.value = RoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun getRoutine(id: String) {
        executeUseCase(
            { getRoutineByIdUseCase.execute(GetRoutineByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { RoutineDialogEvent.GetRoutine(it) }
                    ?: run { RoutineDialogEvent.SomethingWentWrong }
            },
            { _event.value = RoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun editRoutine(id: String, title: String, description: String) {
        executeUseCase(
            { editRoutineUseCase.execute(EditRoutineUseCase.Params(id, title, description)) },
            { _event.value = RoutineDialogEvent.FlowCompleted },
            { _event.value = RoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun deleteRoutine(id: String) {
        executeUseCase(
            { deleteRoutineUseCase.execute(DeleteRoutineUseCase.Params(id)) },
            { _event.value = RoutineDialogEvent.FlowCompleted },
            { _event.value = RoutineDialogEvent.SomethingWentWrong }
        )
    }
}