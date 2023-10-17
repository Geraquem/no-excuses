package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.models.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: INotesRepository) :
    BaseUseCase<GetNoteByIdUseCase.Params, Note?>() {

    override suspend fun execute(params: Params): Note? = repository.getNoteById(params.id)

    data class Params(
        val id: String
    )
}