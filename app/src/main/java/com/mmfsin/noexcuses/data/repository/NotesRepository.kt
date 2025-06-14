package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toNote
import com.mmfsin.noexcuses.data.mappers.toNoteList
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.utils.ID
import io.realm.kotlin.where
import java.util.UUID
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : INotesRepository {

    override fun getNotes(): List<Note> {
        val notes = realmDatabase.getObjectsFromRealm { where<NoteDTO>().findAll() }
        return if (notes.isNotEmpty()) notes.sortedBy { it.date }.toNoteList()
            .reversed() else emptyList()
    }

    override fun getNoteById(id: String): Note? {
        val note = getNoteDTO(id)
        return note?.toNote() ?: run { null }
    }

    override fun addNote(title: String, description: String, date: Long) {
        val id = UUID.randomUUID().toString()
        val note = NoteDTO(id, title, description, date, pinned = false)
        realmDatabase.addObject { note }
    }

    override fun editNote(id: String, title: String, description: String, date: Long) {
        val note = getNoteDTO(id)
        note?.let {
            it.title = title
            it.description = description
            it.date = date
            it.pinned = it.pinned
            realmDatabase.addObject { it }
        }
    }

    override fun pinnedNote(id: String) {
        val notes = realmDatabase.getObjectsFromRealm { where<NoteDTO>().findAll() }
        notes.forEach { note ->
            if (note.id == id) note.pinned = !note.pinned
            else note.pinned = false
            realmDatabase.addObject { note }
        }
    }

    override fun getPinnedNote(): Note? {
        val notes = realmDatabase.getObjectsFromRealm { where<NoteDTO>().findAll() }
        notes.forEach { note -> if (note.pinned) return note.toNote() }
        return null
    }

    override fun deleteNote(id: String) {
        realmDatabase.deleteObject(NoteDTO::class.java, ID, id)
    }

    private fun getNoteDTO(id: String): NoteDTO? =
        realmDatabase.getObjectFromRealm(NoteDTO::class.java, ID, id)
}