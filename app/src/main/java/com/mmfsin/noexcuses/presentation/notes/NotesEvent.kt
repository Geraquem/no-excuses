package com.mmfsin.noexcuses.presentation.notes

import com.mmfsin.noexcuses.domain.models.Note

sealed class NotesEvent {
    class GetNotes(val notes: List<Note>) : NotesEvent()
    object UpdatePushPin : NotesEvent()
    object SWW : NotesEvent()
}