package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : BaseViewModel<MenuEvent>() {

    fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase.execute() },
            { result ->
                _event.value = result?.let { MenuEvent.GetMenu(result) }
                    ?: run { MenuEvent.SomethingWentWrong }
            },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }
}