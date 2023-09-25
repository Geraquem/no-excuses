package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.domain.models.Question

sealed class MenuEvent {
    class GetMenu(val questions: List<Question>): MenuEvent()
    object SomethingWentWrong : MenuEvent()
}