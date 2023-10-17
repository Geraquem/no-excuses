package com.mmfsin.noexcuses.presentation.notes

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.FirstTimeUseCase
import com.mmfsin.noexcuses.domain.usecases.GetNotesUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : BaseViewModel<NotesEvent>() {

    fun getNotes() {
        executeUseCase(
            { getNotesUseCase.execute() },
            { result -> _event.value = NotesEvent.GetNotes(result) },
            { _event.value = NotesEvent.SomethingWentWrong }
        )
    }
}