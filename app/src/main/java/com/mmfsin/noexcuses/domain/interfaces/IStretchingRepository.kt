package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Stretching

interface IStretchingRepository {
    fun getStretching(category: String): List<Stretching>
}