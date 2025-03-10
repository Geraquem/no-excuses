package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_NOTE
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType.UNPIN_ROUTINE
import javax.inject.Inject

class UnpinDataFromMenuUseCase @Inject constructor(
    private val repository: IMenuRepository,
) : BaseUseCase<UnpinDataFromMenuUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        return when (params.type) {
            UNPIN_ROUTINE -> repository.unpinRoutineFromMenu(params.dataId)
            UNPIN_NOTE -> repository.unpinNoteFromMenu(params.dataId)
        }
    }

    data class Params(
        val dataId: String,
        val type: UnpinType
    )
}