package com.mmfsin.noexcuses.presentation.notes.dialogs

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.DeleteNoteUseCase
import com.mmfsin.noexcuses.domain.usecases.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteNoteViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : BaseViewModel<DeleteNoteEvent>() {

    fun getNoteById(id: String) {
        executeUseCase(
            { getNoteByIdUseCase.execute(GetNoteByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { DeleteNoteEvent.GetNote(it) }
                    ?: run { DeleteNoteEvent.SomethingWentWrong }
            },
            { _event.value = DeleteNoteEvent.SomethingWentWrong }
        )
    }

    fun deleteNote(id: String) {
        executeUseCase(
            { deleteNoteUseCase.execute(DeleteNoteUseCase.Params(id)) },
            { _event.value = DeleteNoteEvent.DeletedCompleted },
            { _event.value = DeleteNoteEvent.SomethingWentWrong }
        )
    }
}