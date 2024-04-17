package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDefaultRoutineDaysUseCase @Inject constructor(
    private val repository: IDefaultRoutinesRepository
) : BaseUseCase<GetDefaultRoutineDaysUseCase.Params, List<Day>?>() {

    override suspend fun execute(params: Params): List<Day> =
        repository.getDefaultDays(params.routineId)

    data class Params(
        val routineId: String
    )
}