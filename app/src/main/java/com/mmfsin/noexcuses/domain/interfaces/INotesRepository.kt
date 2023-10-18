package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Note

interface INotesRepository {
    fun getNotes(): List<Note>
    fun getNoteById(id: String): Note?
    fun addNote(title: String, description: String, date: String)
    fun editNote(id: String, title: String, description: String, date: String)
    fun deleteNote(id: String)
}