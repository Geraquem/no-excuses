package com.mmfsin.noexcuses.presentation.myroutines.mdays.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayConfigViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val editDayUseCase: EditDayUseCase,
    private val deleteDayUseCase: DeleteDayUseCase
) : BaseViewModel<DayConfigEvent>() {

    fun addDay(routineId: String, title: String) {
        executeUseCase(
            { addDayUseCase.execute(AddDayUseCase.Params(routineId, title)) },
            { _event.value = DayConfigEvent.FlowCompleted },
            { _event.value = DayConfigEvent.SWW }
        )
    }

    fun getDay(dayId: String) {
        executeUseCase(
            { getDayByIdUseCase.execute(GetDayByIdUseCase.Params(dayId)) },
            { result ->
                _event.value = result?.let { DayConfigEvent.GetDay(it) }
                    ?: run { DayConfigEvent.SWW }
            },
            { _event.value = DayConfigEvent.SWW }
        )
    }

    fun editDay(id: String, title: String) {
        executeUseCase(
            { editDayUseCase.execute(EditDayUseCase.Params(id, title)) },
            { _event.value = DayConfigEvent.FlowCompleted },
            { _event.value = DayConfigEvent.SWW }
        )
    }

    fun deleteDay(id: String) {
        executeUseCase(
            { deleteDayUseCase.execute(DeleteDayUseCase.Params(id)) },
            { _event.value = DayConfigEvent.FlowCompleted },
            { _event.value = DayConfigEvent.SWW }
        )
    }
}