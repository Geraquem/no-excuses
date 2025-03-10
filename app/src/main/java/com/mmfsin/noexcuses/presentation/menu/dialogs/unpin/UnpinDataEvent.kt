package com.mmfsin.noexcuses.presentation.menu.dialogs.unpin

sealed class UnpinDataEvent {
    object Unpinned : UnpinDataEvent()
    object SWW : UnpinDataEvent()
}