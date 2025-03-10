package com.mmfsin.noexcuses.presentation.menu.dialogs.unpin

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.UnpinDataFromMenuUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateNotePushPinUseCase
import com.mmfsin.noexcuses.presentation.menu.dialogs.unpin.UnpinDataDialog.Companion.UnpinType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnpinDataViewModel @Inject constructor(
    private val unpinDataFromMenuUseCase: UnpinDataFromMenuUseCase,
) : BaseViewModel<UnpinDataEvent>() {

    fun unpinData(dataId: String, type: UnpinType) {
        executeUseCase(
            { unpinDataFromMenuUseCase.execute(UnpinDataFromMenuUseCase.Params(dataId, type)) },
            { _event.value = UnpinDataEvent.Unpinned },
            { _event.value = UnpinDataEvent.SWW }
        )
    }
}