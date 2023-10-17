package com.mmfsin.noexcuses.presentation.notes.detail

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : BaseViewModel<NoteDetailEvent>() {

    fun getNoteById(id: String) {
        executeUseCase(
            { getNoteByIdUseCase.execute(GetNoteByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { NoteDetailEvent.GetNote(it) }
                    ?: run { NoteDetailEvent.SomethingWentWrong }
            },
            { _event.value = NoteDetailEvent.SomethingWentWrong }
        )
    }
}