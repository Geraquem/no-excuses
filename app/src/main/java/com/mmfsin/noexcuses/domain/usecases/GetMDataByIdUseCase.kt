package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.models.MData
import javax.inject.Inject

class GetMDataByIdUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<GetMDataByIdUseCase.Params, MData?>() {

    override suspend fun execute(params: Params): MData? = repository.getMDataById(params.mDataId)

    data class Params(
        val mDataId: String
    )
}