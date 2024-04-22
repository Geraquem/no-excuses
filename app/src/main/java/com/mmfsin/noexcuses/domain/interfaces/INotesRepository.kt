package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Note

interface INotesRepository {
    fun getNotes(): List<Note>
    fun getNoteById(id: String): Note?
    fun addNote(title: String, description: String, date: Long)
    fun editNote(id: String, title: String, description: String, date: Long)
    fun pinnedNote(id: String)
    fun getPinnedNote(): Note?
    fun deleteNote(id: String)
}