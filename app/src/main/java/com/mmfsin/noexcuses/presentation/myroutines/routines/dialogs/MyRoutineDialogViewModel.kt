package com.mmfsin.noexcuses.presentation.myroutines.routines.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddMyRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteMyRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.EditMyRoutineUseCase
import com.mmfsin.noexcuses.domain.usecases.GetMyRoutineByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyRoutineDialogViewModel @Inject constructor(
    private val addMyRoutineUseCase: AddMyRoutineUseCase,
    private val getMyRoutineByIdUseCase: GetMyRoutineByIdUseCase,
    private val editMyRoutineUseCase: EditMyRoutineUseCase,
    private val deleteMyRoutineUseCase: DeleteMyRoutineUseCase
) : BaseViewModel<MyRoutineDialogEvent>() {

    fun addRoutine(title: String, description: String) {
        executeUseCase(
            { addMyRoutineUseCase.execute(AddMyRoutineUseCase.Params(title, description)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun getRoutine(id: String) {
        executeUseCase(
            { getMyRoutineByIdUseCase.execute(GetMyRoutineByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { MyRoutineDialogEvent.GetMyRoutine(it) }
                    ?: run { MyRoutineDialogEvent.SomethingWentWrong }
            },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun editRoutine(id: String, title: String, description: String) {
        executeUseCase(
            { editMyRoutineUseCase.execute(EditMyRoutineUseCase.Params(id, title, description)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }

    fun deleteRoutine(id: String) {
        executeUseCase(
            { deleteMyRoutineUseCase.execute(DeleteMyRoutineUseCase.Params(id)) },
            { _event.value = MyRoutineDialogEvent.FlowCompleted },
            { _event.value = MyRoutineDialogEvent.SomethingWentWrong }
        )
    }
}