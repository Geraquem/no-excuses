package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.models.Note
import javax.inject.Inject

class GetPinnedNoteUseCase @Inject constructor(
    private val repository: INotesRepository
) : BaseUseCaseNoParams<Note?>() {

    override suspend fun execute(): Note? = repository.getPinnedNote()
}