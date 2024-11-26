package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Stretching

interface IStretchingRepository {
    suspend fun getStretching(category: String): List<Stretching>
}