package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.domain.models.MenuItem

sealed class MenuEvent {
    object Completed : MenuEvent()
    class MenuItems(val items: List<MenuItem>) : MenuEvent()
    object SomethingWentWrong : MenuEvent()
}