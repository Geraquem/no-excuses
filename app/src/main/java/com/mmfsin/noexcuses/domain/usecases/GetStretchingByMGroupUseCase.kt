package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import com.mmfsin.noexcuses.domain.models.Stretching
import javax.inject.Inject

class GetStretchingByMGroupUseCase @Inject constructor(
    private val repository: IStretchingRepository
) : BaseUseCase<GetStretchingByMGroupUseCase.Params, List<Stretching>>() {

    override suspend fun execute(params: Params): List<Stretching> =
        repository.getStretchingByMGroup(params.category)

    data class Params(
        val category: String
    )
}