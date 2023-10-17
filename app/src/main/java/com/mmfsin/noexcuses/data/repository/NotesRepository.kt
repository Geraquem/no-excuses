package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Note
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : INotesRepository {

    override fun getNotes(): List<Note> {
        return emptyList()
    }

    override fun getNoteById(id: String): Note {
        return Note("", "", "", "")
    }
}