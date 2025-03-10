package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine

sealed class MenuEvent {
    object VersionCompleted : MenuEvent()
    class ActualRoutine(val routine: Routine?) : MenuEvent()
    class CalendarSaved(val totalSaved: Int) : MenuEvent()
    class BodyImage(val isWomanImage: Boolean) : MenuEvent()
    class GetMuscularGroups(val mGroups: List<MuscularGroup>) : MenuEvent()
    class PinnedNote(val note: Note?) : MenuEvent()
    object SWW : MenuEvent()
}