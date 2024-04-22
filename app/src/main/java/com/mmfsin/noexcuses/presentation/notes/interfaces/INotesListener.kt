package com.mmfsin.noexcuses.presentation.notes.interfaces

interface INotesListener {
    fun onNoteClick(id: String)
    fun onNoteLongClick(id: String)
    fun updatePinnedNote(id: String)

    fun deletedComplete()
}