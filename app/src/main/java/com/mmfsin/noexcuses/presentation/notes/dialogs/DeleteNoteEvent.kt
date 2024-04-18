package com.mmfsin.noexcuses.presentation.notes.dialogs

import com.mmfsin.noexcuses.domain.models.Note

sealed class DeleteNoteEvent {
    class GetNote(val note: Note) : DeleteNoteEvent()
    object DeletedCompleted : DeleteNoteEvent()
    object SWW : DeleteNoteEvent()
}