package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Routine

interface IMenuRepository {
    suspend fun checkVersion()
    fun isFirstTime(): Boolean

    fun getMyActualRoutine(): Routine?
}