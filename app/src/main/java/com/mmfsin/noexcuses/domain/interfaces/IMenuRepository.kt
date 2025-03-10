package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine

interface IMenuRepository {
    suspend fun checkVersion()

    fun getMyActualRoutine(): Routine?
    fun getMyActualRoutineDays(routineId: String): List<Day>

    fun unpinRoutineFromMenu(routineId: String)
    fun unpinNoteFromMenu(noteId: String)

    fun checkBodyImage(): Boolean
    fun editBodyImage(womanImageSelected: Boolean)
}