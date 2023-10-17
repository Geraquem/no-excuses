package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: INotesRepository) :
    BaseUseCase<DeleteNoteUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteNote(params.id)

    data class Params(
        val id: String
    )
}