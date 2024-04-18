package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.utils.NO_ID_NOTE
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repository: INotesRepository) :
    BaseUseCase<AddNoteUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        params.id?.let { id ->
            val title = params.title
            val desc = params.description
            if (id == NO_ID_NOTE) {
                if (!(title.isEmpty() && desc.isEmpty())) {
                    repository.addNote(params.title, desc, params.date)
                }
            } else repository.editNote(id, params.title, params.description, params.date)
        }
    }

    data class Params(
        val id: String?,
        val title: String,
        val description: String,
        val date: Long
    )
}