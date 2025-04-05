package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Stretch
import com.mmfsin.noexcuses.domain.models.Stretching

interface IStretchingRepository {
    suspend fun getStretchingData(): List<Stretch>
    suspend fun getStretchingByMGroup(category: String): List<Stretching>
}