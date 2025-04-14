package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import javax.inject.Inject

class DeleteMDataByIdUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<DeleteMDataByIdUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteMDataById(params.mDataId)

    data class Params(
        val mDataId: String
    )
}