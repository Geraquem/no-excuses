package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repository: INotesRepository) :
    BaseUseCase<AddNoteUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.addNote(params.title, params.description, params.date)

    data class Params(
        val title: String,
        val description: String,
        val date: String
    )
}