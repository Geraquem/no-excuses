package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetMyActualRoutineDaysUseCase @Inject constructor(
    private val repository: IMenuRepository,
    private val dfRepository: IDefaultRoutinesRepository
) : BaseUseCase<GetMyActualRoutineDaysUseCase.Params, List<Day>>() {

    override suspend fun execute(params: Params): List<Day> {
        val days = repository.getMyActualRoutineDays(params.routineId)
        return days.ifEmpty { dfRepository.getDefaultDays(params.routineId) }
    }

    data class Params(
        val routineId: String
    )
}