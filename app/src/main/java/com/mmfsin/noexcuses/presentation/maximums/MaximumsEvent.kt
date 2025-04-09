package com.mmfsin.noexcuses.presentation.maximums

sealed class MaximumsEvent {
    class GetMaximums(val data: List<String>) : MaximumsEvent()
    object SWW : MaximumsEvent()
}