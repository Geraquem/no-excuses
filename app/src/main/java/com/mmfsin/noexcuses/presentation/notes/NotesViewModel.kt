package com.mmfsin.noexcuses.presentation.notes

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetNotesUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateNotePushPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val updateNotePushPinUseCase: UpdateNotePushPinUseCase
) : BaseViewModel<NotesEvent>() {

    fun getNotes() {
        executeUseCase(
            { getNotesUseCase.execute() },
            { result -> _event.value = NotesEvent.GetNotes(result) },
            { _event.value = NotesEvent.SWW }
        )
    }

    fun updatePinnedNote(id: String) {
        executeUseCase(
            { updateNotePushPinUseCase.execute(UpdateNotePushPinUseCase.Params(id)) },
            { _event.value = NotesEvent.UpdatePushPin },
            { _event.value = NotesEvent.SWW }
        )
    }
}