package com.mmfsin.noexcuses.presentation.maximums

import com.mmfsin.noexcuses.domain.models.MaximumData

sealed class MaximumsEvent {
    class GetMaximums(val data: List<MaximumData>) : MaximumsEvent()
    object SWW : MaximumsEvent()
}