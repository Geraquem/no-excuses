package com.mmfsin.noexcuses.presentation.notes.detail

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.AddNoteUseCase
import com.mmfsin.noexcuses.domain.usecases.GetNoteByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateNotePushPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val addNoteUseCase: AddNoteUseCase,
) : BaseViewModel<NoteDetailEvent>() {

    fun getNoteById(id: String) {
        executeUseCase(
            { getNoteByIdUseCase.execute(GetNoteByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { NoteDetailEvent.GetNote(it) }
                    ?: run { NoteDetailEvent.SWW }
            },
            { _event.value = NoteDetailEvent.SWW }
        )
    }

    fun addNote(id: String?, title: String, description: String, date: Long) {
        executeUseCase(
            { addNoteUseCase.execute(AddNoteUseCase.Params(id, title, description, date)) },
            { _event.value = NoteDetailEvent.NoteCreated },
            { _event.value = NoteDetailEvent.SWW }
        )
    }
}