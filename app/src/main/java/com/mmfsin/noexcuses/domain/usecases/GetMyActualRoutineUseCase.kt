package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetMyActualRoutineUseCase @Inject constructor(
    private val repository: IMenuRepository
) : BaseUseCaseNoParams<Any?>() {

    override suspend fun execute(): Any? = repository.getMyActualRoutine()

    data class Params(
        val routineId: String
    )
}