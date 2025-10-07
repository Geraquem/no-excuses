package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class DeleteDayUseCase @Inject constructor(
    private val routinesRepository: IMyRoutinesRepository,
) : BaseUseCase<DeleteDayUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = routinesRepository.deleteDay(params.id)

    data class Params(
        val id: String
    )
}