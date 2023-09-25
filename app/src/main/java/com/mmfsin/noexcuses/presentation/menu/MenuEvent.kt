package com.mmfsin.noexcuses.presentation.menu

sealed class MenuEvent {
    object Completed: MenuEvent()
    object SomethingWentWrong : MenuEvent()
}