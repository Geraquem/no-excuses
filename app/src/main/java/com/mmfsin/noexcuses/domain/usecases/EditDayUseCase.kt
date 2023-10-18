package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class EditDayUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCase<EditDayUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.editDay(params.id, params.title)

    data class Params(
        val id: String,
        val title: String
    )
}