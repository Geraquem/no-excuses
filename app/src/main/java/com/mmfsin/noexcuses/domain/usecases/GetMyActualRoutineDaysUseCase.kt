package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetMyActualRoutineDaysUseCase @Inject constructor(
    private val repository: IMenuRepository
) : BaseUseCase<GetMyActualRoutineDaysUseCase.Params, List<Day>>() {

    override suspend fun execute(params: Params): List<Day> =
        repository.getMyActualRoutineDays(params.routineId)

    data class Params(
        val routineId: String
    )
}