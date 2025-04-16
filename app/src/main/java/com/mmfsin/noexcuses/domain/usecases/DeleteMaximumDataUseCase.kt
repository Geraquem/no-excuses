package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import javax.inject.Inject

class DeleteMaximumDataUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<DeleteMaximumDataUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteMaximumData(params.exerciseId)

    data class Params(
        val exerciseId: String
    )
}