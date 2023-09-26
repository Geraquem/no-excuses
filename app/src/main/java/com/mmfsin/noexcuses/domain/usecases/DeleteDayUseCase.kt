package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteDayUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCase<DeleteDayUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteDay(params.id)

    data class Params(
        val id: String
    )
}