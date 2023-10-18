package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.EditRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutineByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutineDialogViewModel @Inject constructor(
    private val addRoutineUseCase: AddRoutineUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val editRoutineUseCase: EditRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : BaseViewModel<MyRoutineDialogEvent>() {

    fun addRoutine(title: String, description: String) {
        executeUseCase(
            { addRoutineUseCase.execute(AddRoutineUseCase.Params(title, description)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun getRoutine(id: String) {
        executeUseCase(
            { getRoutineByIdUseCase.execute(GetRoutineByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { MyRoutineDialogEvent.GetMyRoutine(it) }
                    ?: run { MyRoutineDialogEvent.SomethingWentWrong }
            },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun editRoutine(id: String, title: String, description: String) {
        executeUseCase(
            { editRoutineUseCase.execute(EditRoutineUseCase.Params(id, title, description)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun deleteRoutine(id: String) {
        executeUseCase(
            { deleteRoutineUseCase.execute(DeleteRoutineUseCase.Params(id)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }
}