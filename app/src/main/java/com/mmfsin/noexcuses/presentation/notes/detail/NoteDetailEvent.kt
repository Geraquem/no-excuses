package com.mmfsin.noexcuses.presentation.notes.detail

import com.mmfsin.noexcuses.domain.models.Note

sealed class NoteDetailEvent {
    class GetNote(val note: Note) : NoteDetailEvent()
    object NoteCreated : NoteDetailEvent()
    object SomethingWentWrong : NoteDetailEvent()
}