package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.base.BaseViewModel
import com.mmfsin.noexcuses.domain.usecases.CheckVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase
) : BaseViewModel<MenuEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MenuEvent.Completed },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }
}