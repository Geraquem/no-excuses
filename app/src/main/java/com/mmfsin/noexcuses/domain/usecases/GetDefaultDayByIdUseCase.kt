package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDefaultDayByIdUseCase @Inject constructor(
    private val repository: IDefaultRoutinesRepository
) : BaseUseCase<GetDefaultDayByIdUseCase.Params, Day?>() {

    override suspend fun execute(params: Params): Day? =
        repository.getDefaultDayById(params.routineId)

    data class Params(
        val routineId: String
    )
}