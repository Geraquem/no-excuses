package com.mmfsin.noexcuses.presentation.maximums.detail

import com.mmfsin.noexcuses.domain.models.MaximumData

sealed class MaximumDetailEvent {
    class GetData(val data: MaximumData) : MaximumDetailEvent()
    object SWW : MaximumDetailEvent()
}